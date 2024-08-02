package me.jungwuk.koava;

import com.sun.jna.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinNT;
import me.jungwuk.koava.callbacks.*;
import me.jungwuk.koava.constants.KoaCode;
import me.jungwuk.koava.enums.*;
import me.jungwuk.koava.exceptions.COMInitializationException;
import me.jungwuk.koava.exceptions.IllegalKoaResult;
import me.jungwuk.koava.interfaces.KwLibrary;
import me.jungwuk.koava.models.StockInfo;
import me.jungwuk.koava.models.Upjong;
import me.jungwuk.koava.utils.KoavaUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

@SuppressWarnings("FieldCanBeLocal")
public class Koava {
    private static Koava instance;

    private KwLibrary kw;
    private BaseEventHandler baseEventHandler;
    private final ArrayList<KoaEventHandler> eventHandlers = new ArrayList<>();
    private String realKey;

    // 콜백이 GC에 의해 죽지 않도록 하는 장치
    private OnEventConnectCallback onEventConnect;
    private OnReceiveTrDataCallback onReceiveTrData;
    private OnReceiveRealDataCallback onReceiveRealData;
    private OnReceiveMsgCallback onReceiveMsg;
    private OnReceiveChejanDataCallback onReceiveChejanData;
    private OnReceiveRealConditionCallback onReceiveRealCondition;
    private OnReceiveTrConditionCallback onReceiveTrCondition;
    private OnReceiveConditionVerCallback onReceiveConditionVer;

    private boolean initialized = false;

    private Koava() { }

    public static Koava getInstance() {
        if (instance == null) {
            instance = new Koava();
        }
        return instance;
    }

    public void init() throws COMInitializationException {
        if (initialized) return;

        WinNT.HRESULT hr = Ole32.INSTANCE.CoInitializeEx(null, Ole32.COINIT_MULTITHREADED);
        if (COMUtils.FAILED(hr)) {
            throw new COMInitializationException("CoInitializeEx를 실패하였습니다.");
        }

        kw = Native.load("kw_", KwLibrary.class);
        kw.kw_Initialize(1);
        kw.kw_SetCharsetUtf8(1);

        baseEventHandler = new BaseEventHandler();
        eventHandlers.add(baseEventHandler);
        initEventHandler();

        RealTypes.getFid(10); // 캐시를 생성하도록 유도합니다.

        initialized = true;
    }

    public KwLibrary getKw() {
        return this.kw;
    }

    public void addEventHandler(KoaEventHandler handler) {
        if (eventHandlers.contains(handler)) return;
        eventHandlers.add(handler);
    }

    public void removeEventHandler(KoaEventHandler handler) {
        eventHandlers.remove(handler);
    }

    /**
     * 가장 마지막에 받은 Real Data의 realKey를 반환
     */
    public String getLastRealKey() {
        return realKey;
    }

    /**
     * 계좌비밀번호 입력창 출력
     */
    public void showAccountWindow() {
        koaFunctions("ShowAccountWindow", "");
    }

    /**
     * 접속서버 구분을 알려준다
     *
     * @return 1 : 모의투자 접속, 나머지 : 실서버 접속
     */
    public String getServerGubun() {
        return koaFunctions("GetServerGubun", "");
    }

    /**
     * 모의 투자 접속 여부를 확인합니다
     * @return 모의 투자 접속시 true
     */
    public boolean isMockTradingServer() {
        return "1".equals(getServerGubun());
    }

    /**
     * 실서버에 접속한 상태인지 확인합니다
     * @return 실서버 접속시 true
     */
    public boolean isRealServer() {
        return !isMockTradingServer();
    }

    /**
     * 주식종목 시장구분, 종목분류등 정보제공
     *
     * @param code 종목 코드
     * @return 호출결과는 입력한 종목에 대한 대분류, 중분류, 업종구분값을 구분자로 연결한 문자열입니다. <br>구분자는 '|'와 ';'입니다.<br>예시: {@code 시장구분0|거래소;시장구분1|중형주;업종구분|금융업;}
     */
    public String getMasterStockInfoRaw(String code) {
        return koaFunctions("GetMasterStockInfo", code);
    }

    /**
     * 주식종목 시장구분, 종목분류등 정보제공
     *
     * @param code 종목 코드
     * @return 성공시 : StockInfo<br>실패시 : null
     */
    public StockInfo getMasterStockInfo(String code) {
        String data = getMasterStockInfoRaw(code);
        if (data == null || data.isEmpty()) return null;

        ArrayList<StockInfo> list = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(data, ";");

        String sijangGubun0 = "";
        String sijangGubun1 = "";
        String upjongGubun = "";

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (token.isEmpty()) continue;

            StringTokenizer st2 = new StringTokenizer(token, "|");
            String key = st2.nextToken();
            String value = st2.nextToken();

            if (key.equals("시장구분0")) sijangGubun0 = value;
            if (key.equals("시장구분1")) sijangGubun1 = value;
            if (key.equals("업종구분")) upjongGubun = value;
        }

        return new StockInfo(sijangGubun0, sijangGubun1, upjongGubun);
    }



    /**
     *  조검검색 종목코드와 현재가 수신(실시간 조건검색은 사용할수 없음)<br>
     *  조건검색결과에 종목코드와 그 종목의 현재가를 함께 수신하는 방법이며 실시간 조건검색에서는 사용할 수 없고 오직 조건검색에만 사용할수 있습니다.<br>
     *  현재가 포함으로 설정시 OnReceiveTrCondition()이벤트에 "종목코드1^현재가1;종목코드2^현재가2;...종목코드n^현재가n"형식으로 전달됨<br>
     *  <b>실시간 조건검색에서는 사용할 수 없고 수신데이터에 현재가가 포함되므로 데이터처리방법을 달리해야 합니다</b>
     * @param addPrice true : 현재가 수신<br>false : 수신 안 함
     */
    public void setConditionSearchFlag(boolean addPrice) {
        koaFunctions("SetConditionSearchFlag", addPrice ? "AddPrice" : "DelPrice");
    }

    /**
     * 해당 시장의 업종 리스트를 받아옵니다
     * @param sijangGubun 0:코스피, 1: 코스닥, 2:KOSPI200, 4:KOSPI100(KOSPI50), 7:KRX100
     * @return "시장구분값,업종코드,업종명|시장구분값,업종코드,업종명|...|시장구분값,업종코드,업종명|" 형식입니다.<br>
     *           즉 하나의 업종코드는 입력한 시장구분값과 업종코드 그리고 그 업종명이 쉼표(,)로 구분되며 각 업종코드는 '|'로 구분됩니다.
     */
    public String getUpjongCodeRaw(String sijangGubun) {
        return koaFunctions("GetUpjongCode", sijangGubun);
    }

    /**
     * 해당 시장의 업종 리스트를 받아옵니다
     * @param sijangGubun 0:코스피, 1: 코스닥, 2:KOSPI200, 4:KOSPI100(KOSPI50), 7:KRX100
     * @return 업종 리스트
     */
    public List<Upjong> getUpjongCode(String sijangGubun) throws IllegalKoaResult {
        String data = getUpjongCodeRaw(sijangGubun);

        if (data == null || data.isEmpty()) {
            return Collections.emptyList();
        }

        ArrayList<Upjong> upjongs = new ArrayList<>();
        StringTokenizer st1 = new StringTokenizer(data, "|");
        while (st1.hasMoreTokens()) {
            String token = st1.nextToken().trim();

            if (token.isEmpty()) {
                continue;
            }

            StringTokenizer st2 = new StringTokenizer(token, ",");
            if (st2.countTokens() >= 3) {
                String sijang = st2.nextToken();
                String upjongCode = st2.nextToken();
                String upjongName = st2.nextToken();

                while (st2.hasMoreTokens()) {
                    upjongName += "," + st2.nextToken();
                }
                upjongs.add(new Upjong(sijang, upjongCode, upjongName));
            } else {
                throw new IllegalKoaResult("Koa가 잘못된 응답을 반환했습니다. UpjongCode의 응답은 v1,v2,v3|v1,v2,v3|...와 같은 형식이어야 하지만, 응답 내용은 그렇지 않습니다. 받은 데이터 : \"" + data + "\"");
            }
        }

        return upjongs;
    }

    /**
     * 해당 시장의 업종 리스트를 받아옵니다
     * @param sijangType 시장 타입
     * @return 업종 리스트
     */
    public List<Upjong> getUpjongCode(SijangType sijangType) {
        return getUpjongCode(sijangType.getGubunCode());
    }

    /**
     * 모든 시장의 업종들을 받아옵니다
     * @return 업종 리스트
     */
    public List<Upjong> getAllUpjongCodes() {
        ArrayList<Upjong> upjongs = new ArrayList<>();

        for (SijangType sijang : SijangType.values()) {
            if (sijang == SijangType.UNKNOWN) continue;
            upjongs.addAll(getUpjongCode(sijang));
        }

        return upjongs;
    }

    /**
     * 업종 코드로 업종 이름을 받아옵니다.
     * @param upjongCode 업종 코드
     * @return 업종명
     */
    public String getUpjongNameByCode(String upjongCode) {
        return koaFunctions("GetUpjongNameByCode", upjongCode);
    }

    /**
     * 특정 종목이 투자유의종목인지 아래와 같은 방법으로 확인할 수 있습니다.
     * @param code 종목 코드
     * @return 투자유의 종목인 경우 "1" 값이 리턴, 그렇지 않은 경우 "0" 값 리턴. (ETF가 아닌 종목을 입력시 "0" 값 리턴.)
     */
    public String isOrderWarningETFRaw(String code) {
        return koaFunctions("IsOrderWarningETF", code);
    }

    /**
     * 특정 종목이 투자유의종목인지 아래와 같은 방법으로 확인할 수 있습니다.
     * @param code 종목 코드
     * @return {@code true} : 투자 유의<br> {@code false} : 유의 상태가 아니거나 ETF가 아님
     */
    public boolean isOrderWarningETF(String code) {
        return "1".equals(isOrderWarningETFRaw(code));
    }

    /**
     * 특정 종목이 투자유의종목인지 아래와 같은 방법으로 확인할 수 있습니다.
     * @param code 종목 코드
     * @return "0":해당없음, "2":정리매매, "3":단기과열, "4":투자위험, "5":투자경고
     */
    public String isOrderWarningStockRaw(String code) {
        return koaFunctions("IsOrderWarningStock", code);
    }

    /**
     * 특정 종목이 투자유의종목인지 아래와 같은 방법으로 확인할 수 있습니다.
     * @param code 종목 코드
     * @return 어떤 상태의 Warning 인지 구분할 수 있는 StockWarningType
     */
    public StockWarningType isOrderWarningStock(String code) {
        return StockWarningType.valueOf(isOrderWarningStockRaw(code));
    }

    /**
     * 상장주식수를 <b>long 타입</b>으로 구합니다
     * @param code 종목 코드
     * @return 상장주식수, <b>조회 실패시 -1</b>
     */
    public long getMasterListedStockCntEx(String code) {
        long ret;
        String cnt = koaFunctions("GetMasterListedStockCntEx", code);

        if (cnt == null || cnt.trim().isEmpty()) {
            ret = -1;
        } else {
            ret = Long.parseLong(cnt);
        }

        return ret;
    }

    public String getStockMarketKind(String code) {
        return koaFunctions("GetStockMarketKind", code);
    }


    public void uninitialize() {
        kw.kw_Uninitialize();
        initialized = false;
        eventHandlers.clear();
    }

    public void setOnEventConnect(OnEventConnectCallback handler) {
        baseEventHandler.onEventConnect = handler;
    }

    public void setOnReceiveTrData(OnReceiveTrDataCallback handler) {
        baseEventHandler.onReceiveTrData = handler;
    }

    public void setOnReceiveRealData(OnReceiveRealDataCallback handler) {
        baseEventHandler.onReceiveRealData = handler;
    }

    public void setOnReceiveMsg(OnReceiveMsgCallback handler) {
        baseEventHandler.onReceiveMsg = handler;
    }

    public void setOnReceiveChejanData(OnReceiveChejanDataCallback handler) {
        baseEventHandler.onReceiveChejanData = handler;
    }

    public void setOnReceiveRealCondition(OnReceiveRealConditionCallback handler) {
        baseEventHandler.onReceiveRealCondition = handler;
    }

    public void setOnReceiveTrCondition(OnReceiveTrConditionCallback handler) {
        baseEventHandler.onReceiveTrCondition = handler;
    }

    public void setOnReceiveConditionVer(OnReceiveConditionVerCallback handler) {
        baseEventHandler.onReceiveConditionVer = handler;
    }

    public void setCharsetUtf8(boolean useUtf8) {
        kw.kw_SetCharsetUtf8(useUtf8 ? 1 : 0);
    }

    /**
     * 로그인 윈도우를 실행한다.
     * <br>
     * 로그인이 성공하거나 실패하는 경우 OnEventConnect 이벤트가 발생
     * @return 오류코드
     */
    public KoaCode commConnect() {
        return new KoaCode(kw.kw_CommConnect());
    }

    public int getConnectStateRaw() {
        return kw.kw_GetConnectState();
    }

    public ConnectStateType getConnectState() {
        int state = getConnectStateRaw();

        switch (state) {
            case 0:
                return ConnectStateType.DISCONNECTED;
            case 1:
                return ConnectStateType.CONNECTED;
            default:
                return ConnectStateType.UNKNOWN;
        }
    }

    public String getMasterCodeName(String code) {
        Pointer p = kw.kw_GetMasterCodeNameA(code);
        return getAStringAndFree(p);
    }

    /**
     * 상장주식수를 구합니다. 일부 종목에서 overflow가 발생할 수 있습니다.
     * <br>long 타입으로 받아오는 메소드를 사용할 것을 권장합니다.
     * @see Koava#getMasterListedStockCntEx(String code)
     * @param code 종목 코드
     * @return 상장주식수, <b>조회 실패시 -1</b>
     */
    public int getMasterListedStockCnt(String code) {
        return kw.kw_GetMasterListedStockCntA(code);
    }

    public String getMasterConstruction(String code) {
        Pointer p = kw.kw_GetMasterConstructionA(code);
        return getAStringAndFree(p);
    }

    public String getMasterListedStockDate(String code) {
        Pointer p = kw.kw_GetMasterListedStockDateA(code);
        return getAStringAndFree(p);
    }

    public String getMasterLastPrice(String code) {
        Pointer p = kw.kw_GetMasterLastPriceA(code);
        return getAStringAndFree(p);
    }

    public String getMasterStockState(String code) {
        Pointer p = kw.kw_GetMasterStockStateA(code);
        return getAStringAndFree(p);
    }

    public int getDataCount(String strRecordName) {
        return kw.kw_GetDataCountA(strRecordName);
    }

    public String getOutputValue(String recordName, int repeatIdx, int itemIdx) {
        Pointer p = kw.kw_GetOutputValueA(recordName, repeatIdx, itemIdx);
        return getAStringAndFree(p);
    }

    public String getCommData(String trCode, String recordName, int index, String itemName) {
        Pointer p = kw.kw_GetCommDataA(trCode, recordName, index, itemName);
        return getAStringAndFree(p);
    }

    public String getCommRealData(String code, int fid) {
        Pointer p = kw.kw_GetCommRealDataA(code, fid);
        return getAStringAndFree(p);
    }

    public String getCommRealData(String code, RealTypes.FID fid) {
        return getCommRealData(code, fid.getId());
    }

    public String getChejanData(int fid) {
        Pointer p = kw.kw_GetChejanDataA(fid);
        return getAStringAndFree(p);
    }

    public String getChejanData(RealTypes.FID fid) {
        return getChejanData(fid.getId());
    }

    public String getAPIModulePath() {
        Pointer p = kw.kw_GetAPIModulePathA();
        return getAStringAndFree(p);
    }

    public String getCodeListByMarket(String market) {
        Pointer p = kw.kw_GetCodeListByMarketA(market);
        return getAStringAndFree(p);
    }

    public String getFutureList() {
        Pointer p = kw.kw_GetFutureListA();
        return getAStringAndFree(p);
    }

    public String getActPriceList() {
        Pointer p = kw.kw_GetActPriceListA();
        return getAStringAndFree(p);
    }

    public String getMonthList() {
        Pointer p = kw.kw_GetMonthListA();
        return getAStringAndFree(p);
    }

    public String getOptionCode(String actPrice, OptionType cp, String month) {
        Pointer p = kw.kw_GetOptionCodeA(actPrice, cp.getValue(), month);
        return getAStringAndFree(p);
    }

    public String getOptionCodeByMonth(String code, OptionType cp, String month) {
        Pointer p = kw.kw_GetOptionCodeByMonthA(code, cp.getValue(), month);
        return getAStringAndFree(p);
    }

    public String getOptionCodeByActPrice(String code, OptionType cp, int tick) {
        Pointer p = kw.kw_GetOptionCodeByActPriceA(code, cp.getValue(), tick);
        return getAStringAndFree(p);
    }

    public String getSFutureList(String baseAssetCode) {
        Pointer p = kw.kw_GetSFutureListA(baseAssetCode);
        return getAStringAndFree(p);
    }

    public String getSFutureCodeByIndex(String baseAssetCode, int index) {
        Pointer p = kw.kw_GetSFutureCodeByIndexA(baseAssetCode, index);
        return getAStringAndFree(p);
    }

    public String getSActPriceList(String baseAssetGb) {
        Pointer p = kw.kw_GetSActPriceListA(baseAssetGb);
        return getAStringAndFree(p);
    }

    public String getSMonthList(String baseAssetGb) {
        Pointer p = kw.kw_GetSMonthListA(baseAssetGb);
        return getAStringAndFree(p);
    }

    public String getSOptionCode(String baseAssetGb, String actPrice, OptionType cp, String month) {
        Pointer p = kw.kw_GetSOptionCodeA(baseAssetGb, actPrice, cp.getValue(), month);
        return getAStringAndFree(p);
    }

    public String getSOptionCodeByMonth(String strBaseAssetGb, String code, OptionType cp, String month) {
        Pointer p = kw.kw_GetSOptionCodeByMonthA(strBaseAssetGb, code, cp.getValue(), month);
        return getAStringAndFree(p);
    }

    public String getSOptionCodeByActPrice(String baseAssetGb, String code, OptionType cp, int tick) {
        Pointer p = kw.kw_GetSOptionCodeByActPriceA(baseAssetGb, code, cp.getValue(), tick);
        return getAStringAndFree(p);
    }

    public String getFutureCodeByIndex(int index) {
        Pointer p = kw.kw_GetFutureCodeByIndexA(index);
        return getAStringAndFree(p);
    }

    public String getThemeGroupList(ThemeGroupListSortingType type) {
        Pointer p = kw.kw_GetThemeGroupListA(type.getTypeCode());
        return getAStringAndFree(p);
    }

    public String getThemeGroupCode(String themeCode) {
        Pointer p = kw.kw_GetThemeGroupCodeA(themeCode);
        return getAStringAndFree(p);
    }

    public String getSFOBasisAssetList() {
        Pointer p = kw.kw_GetSFOBasisAssetListA();
        return getAStringAndFree(p);
    }

    public String getOptionATM() {
        Pointer p = kw.kw_GetOptionATMA();
        return getAStringAndFree(p);
    }

    public String getSOptionATM(String baseAssetGb) {
        Pointer p = kw.kw_GetSOptionATMA(baseAssetGb);
        return getAStringAndFree(p);
    }

    public String getBranchCodeName() {
        Pointer pointer = kw.kw_GetBranchCodeNameA();
        return getAStringAndFree(pointer);
    }

    public KoaCode sendOrderCredit(String rqName, String screenNo, String accNo, int orderType, String code, int qty, int price, HogaType hogaGb, String creditGb, String loanDate, String orgOrderNo) {
        return new KoaCode(kw.kw_SendOrderCreditA(rqName, screenNo, accNo, orderType, code, qty, price, hogaGb.getCode(), creditGb, loanDate, orgOrderNo));
    }

    public String koaFunctions(String functionName, String param) {
        Pointer p = kw.kw_KOA_FunctionsA(functionName, param);
        return getAStringAndFree(p);
    }

    public int setInfoData(String infoData) {
        return kw.kw_SetInfoDataA(infoData);
    }

    public int setRealReg(String screenNo, String codeList, String fidList, String optType) {
        return kw.kw_SetRealRegA(screenNo, codeList, fidList, optType);
    }

    public int setRealReg(String screenNo, String codeList, String fidList, RealRegistOption option) {
        return setRealReg(screenNo, codeList, fidList, option == RealRegistOption.KEEP ? "1" : "0");
    }

    public int setRealReg(String screenNo, String codeList, RealTypes.FID fid, RealRegistOption option) {
        return setRealReg(screenNo, codeList, Integer.toString(fid.getId()), option == RealRegistOption.KEEP ? "1" : "0");
    }

    public int setRealReg(String screenNo, String codeList, Iterable<RealTypes.FID> fidIterable, RealRegistOption option) {
        String sList = KoavaUtils.fidIterableToStrList(fidIterable);
        return setRealReg(screenNo, codeList, sList, option);
    }

    public int setRealReg(String screenNo, String codeList, List<RealTypes.FID> fidList, RealRegistOption option) {
        String sList = KoavaUtils.fidListToStrList(fidList);
        return setRealReg(screenNo, codeList, sList, option);
    }

    public int setRealReg(String screenNo, String codeList, RealTypes.FID[] fidList, RealRegistOption option) {
        String sList = KoavaUtils.fidListToStrList(fidList);
        return setRealReg(screenNo, codeList, sList, option);
    }

    public int getConditionLoad() {
        return kw.kw_GetConditionLoad();
    }

    public String getConditionNameList() {
        Pointer p = kw.kw_GetConditionNameListA();
        return getAStringAndFree(p);
    }

    public int sendCondition(String scrNo, String conditionName, int index, int search) {
        return kw.kw_SendConditionA(scrNo, conditionName, index, search);
    }

    public void sendConditionStop(String scrNo, String conditionName, int index) {
        kw.kw_SendConditionStopA(scrNo, conditionName, index);
    }

    public Variant.VARIANT getCommDataEx(String trCode, String recordName) {
        return kw.kw_GetCommDataExA(trCode, recordName);
    }

    public void setRealRemove(String scrNo, String delCode) {
        kw.kw_SetRealRemoveA(scrNo, delCode);
    }

    public int getMarketType(String trCode) {
        return kw.kw_GetMarketTypeA(trCode);
    }

    public KoaCode commRqData(String rqName, String trCode, int prevNext, String screenNo) {
        return new KoaCode(kw.kw_CommRqDataA(rqName, trCode, prevNext, screenNo));
    }

    public String getLoginInfo(LoginInfoTag tag) {
        Pointer pointer = kw.kw_GetLoginInfoA(tag.name());
        return getAStringAndFree(pointer);
    }

    public KoaCode sendOrder(String rqName, String screenNo, String accNo, int orderType, String code, int qty, int price, HogaType hogaGb, String orgOrderNo) {
        return new KoaCode(kw.kw_SendOrderA(rqName, screenNo, accNo, orderType, code, qty, price, hogaGb.getCode(), orgOrderNo));
    }

    public KoaCode sendOrderFO(String rqName, String screenNo, String accNo, String code, int ordKind, String slbyTp, String ordTp, int qty, String price, String orgOrdNo) {
        return new KoaCode(kw.kw_SendOrderFOA(rqName, screenNo, accNo, code, ordKind, slbyTp, ordTp, qty, price, orgOrdNo));
    }

    public void setInputValue(String id, String value) {
        kw.kw_SetInputValueA(id, value);
    }

    public void disconnectRealData(String scnNo) {
        kw.kw_DisconnectRealDataA(scnNo);
    }

    public int getRepeatCnt(String code, String recordName) {
        return kw.kw_GetRepeatCntA(code, recordName);
    }

    public KoaCode commKwRqData(String arrCode, boolean next, int codeCount, int typeFlag, String rqName, String screenNo) {
        return new KoaCode(kw.kw_CommKwRqDataA(arrCode, next ? 1 : 0, codeCount, typeFlag, rqName, screenNo));
    }

    public KoaCode commKwRqData(List<String> codeList, boolean next, int codeCount, int typeFlag, String rqName, String screenNo) {
        StringBuilder sb = new StringBuilder();
        int listSize = codeList.size();

        for (int i = 0; i < listSize; i++) {
            if (i != 0) sb.append(";");
            sb.append(codeList.get(i));
        }

        return commKwRqData(sb.toString(), next, codeCount, typeFlag, rqName, screenNo);
    }

    public KoaCode commKwRqData(String[] codeArr, boolean next, int codeCount, int typeFlag, String rqName, String screenNo) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < codeArr.length; i++) {
            if (i != 0) sb.append(";");
            sb.append(codeArr[i]);
        }

        return commKwRqData(sb.toString(), next, codeCount, typeFlag, rqName, screenNo);
    }

    public void waitDisconnection() {
        kw.kw_Wait();
    }

    public void sleep(int msec) {
        kw.kw_Sleep(msec);
    }

    public void disconnect() {
        kw.kw_Disconnect();
    }

    private String getAStringAndFree(Pointer p) {
        String ret = p.getString(0);
        kw.kw_FreeStringA(p);
        return ret;
    }

    // 모든 이벤트 핸들러에게 이벤트를 뿌려주도록 합니다.
    // 더 좋은 아이디어를 이슈에 남겨주시면 정말 감사하겠습니다...
    private void initEventHandler() {
        onEventConnect = (OnEventConnectCallback) errCode -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onEventConnect(errCode);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveTrData = (OnReceiveTrDataCallback) (scrNo, rqName, trCode, recordName, prevNext, dataLength, errorCode, message, splmMsg) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveTrData(scrNo, rqName, trCode, recordName, prevNext, dataLength, errorCode, message, splmMsg);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveRealData = (OnReceiveRealDataCallback) (realKey, realType, realData) -> {
            this.realKey = realKey;

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveRealData(realKey, realType, realData);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveMsg = (OnReceiveMsgCallback) (scrNo, rqName, trCode, msg) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveMsg(scrNo, rqName, trCode, msg);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveChejanData = (OnReceiveChejanDataCallback) (gubun, itemCnt, fIdList) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveChejanData(gubun, itemCnt, fIdList);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveRealCondition = (OnReceiveRealConditionCallback) (trCode, type, conditionName, conditionIndex) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveRealCondition(trCode, type, conditionName, conditionIndex);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveTrCondition = (OnReceiveTrConditionCallback) (scrNo, codeList, conditionName, index, next) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveTrCondition(scrNo, codeList, conditionName, index, next);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveConditionVer = (OnReceiveConditionVerCallback) (ret, msg) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveConditionVer(ret, msg);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        kw.kw_SetOnEventConnect(onEventConnect);
        kw.kw_SetOnReceiveTrDataA(onReceiveTrData);
        kw.kw_SetOnReceiveRealDataA(onReceiveRealData);
        kw.kw_SetOnReceiveMsgA(onReceiveMsg);
        kw.kw_SetOnReceiveChejanDataA(onReceiveChejanData);
        kw.kw_SetOnReceiveRealConditionA(onReceiveRealCondition);
        kw.kw_SetOnReceiveTrConditionA(onReceiveTrCondition);
        kw.kw_SetOnReceiveConditionVerA(onReceiveConditionVer);
    }

}

class BaseEventHandler extends KoaEventHandler {
    OnEventConnectCallback onEventConnect;
    OnReceiveTrDataCallback onReceiveTrData;
    OnReceiveRealDataCallback onReceiveRealData;
    OnReceiveMsgCallback onReceiveMsg;
    OnReceiveChejanDataCallback onReceiveChejanData;
    OnReceiveRealConditionCallback onReceiveRealCondition;
    OnReceiveTrConditionCallback onReceiveTrCondition;
    OnReceiveConditionVerCallback onReceiveConditionVer;

    public void onEventConnect(int errCode) {
        if (onEventConnect == null) return;
        onEventConnect.invoke(errCode);
    }

    public void onReceiveChejanData(String gubun, int itemCnt, String fIdList) {
        if (onReceiveChejanData == null) return;
        onReceiveChejanData.invoke(gubun, itemCnt, fIdList);
    }

    public void onReceiveConditionVer(int ret, String msg) {
        if (onReceiveConditionVer == null) return;
        onReceiveConditionVer.invoke(ret, msg);
    }

    public void onReceiveMsg(String scrNo, String rqName, String trCode, String msg) {
        if (onReceiveMsg == null) return;
        onReceiveMsg.invoke(scrNo, rqName, trCode, msg);
    }

    public void onReceiveRealCondition(String trCode, String type, String conditionName, String conditionIndex) {
        if (onReceiveRealCondition == null) return;
        onReceiveRealCondition.invoke(trCode, type, conditionName, conditionIndex);
    }

    public void onReceiveRealData(String realKey, String realType, String realData) {
        if (onReceiveRealData == null) return;
        onReceiveRealData.invoke(realKey, realType, realData);
    }

    public void onReceiveTrCondition(String scrNo, String codeList, String conditionName, int index, int next) {
        if (onReceiveTrCondition == null) return;
        onReceiveTrCondition.invoke(scrNo, codeList, conditionName, index, next);
    }

    public void onReceiveTrData(String scrNo, String rqName, String trCode,
                                String recordName, String prevNext, int dataLength,
                                String errorCode, String message, String splmMsg) {
        if (onReceiveTrData == null) return;
        onReceiveTrData.invoke(scrNo, rqName, trCode, recordName, prevNext, dataLength, errorCode, message, splmMsg);
    }
}
