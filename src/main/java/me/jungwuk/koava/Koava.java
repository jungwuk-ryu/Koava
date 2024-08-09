package me.jungwuk.koava;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinNT;
import me.jungwuk.koava.constants.KoaCode;
import me.jungwuk.koava.enums.*;
import me.jungwuk.koava.exceptions.COMInitializationException;
import me.jungwuk.koava.exceptions.IllegalKoaResult;
import me.jungwuk.koava.handlers.*;
import me.jungwuk.koava.interfaces.KwLibrary;
import me.jungwuk.koava.interfaces.callbacks.*;
import me.jungwuk.koava.models.BasisAssetCodeAndStockName;
import me.jungwuk.koava.models.CondIdxAndName;
import me.jungwuk.koava.models.StockInfo;
import me.jungwuk.koava.models.Upjong;
import me.jungwuk.koava.models.event.*;
import me.jungwuk.koava.utils.KoavaUtils;
import me.jungwuk.koava.waiters.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;


@SuppressWarnings({"FieldCanBeLocal", "UnusedReturnValue"})
public class Koava {
    private static Koava instance;
    private final ArrayList<KoaEventHandler> eventHandlers = new ArrayList<>();
    private KwLibrary kw;
    private BaseEventHandler baseEventHandler;
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

    private Koava() {
    }

    public static Koava getInstance() {
        if (instance == null) {
            instance = new Koava();
        }
        return instance;
    }

    /**
     * Koava및 관련 라이브러리를 초기화합니다
     * @throws COMInitializationException 라이브러리 초기화 실패시 예외 발생
     */
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

    /**
     * Kw_ 인스턴스를 받습니다
     * @return KwLibrary 인스턴스
     */
    public KwLibrary getKw() {
        return this.kw;
    }

    /**
     * 이벤트 핸들러를 추가합니다
     * @param handler 이벤트 핸들러
     */
    public void addEventHandler(KoaEventHandler handler) {
        if (eventHandlers.contains(handler)) return;
        eventHandlers.add(handler);
    }

    /**
     * 이벤트 핸들러를 제거합니다
     * @param handler 이벤트 핸들러
     */
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
    @SuppressWarnings("StringConcatenationInLoop")
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


                /* 몇 업종은 "게임,문화"와 같이 ","를 이름에 포함하고 있습니다.
                * 아래 코드는 이를 처리하기 위함입니다. */
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

    /**
     * <b>종목코드로 Market구분 구하기</b><br>
     * 종목코드 입력으로 해당 종목이 어느 시장에 포함되어 있는지 구하는 기능<br>
     * 서버와의 통신없이 메모리에 상주하는 값을 사용하므로 횟수제한 등은 없습니다.<br>
     *
     * @param code 종목 코드
     * @return {@code 0}:코스피<br> {@code 10}:코스닥<br> {@code 3}:ELW<br> {@code 8}:ETF<br>
     *          {@code 4}/{@code 14}:뮤추얼펀드<br> {@code 6}/{@code 16}:리츠<br> {@code 9}/{@code 19}:하이일드펀드<br>
     *          {@code 30}:제3시장<br> {@code 60}:ETN
     */
    public String getStockMarketKindRaw(String code) {
        return koaFunctions("GetStockMarketKind", code);
    }

    /**
     * <b>종목코드로 Market구분 구하기</b><br>
     * 종목코드 입력으로 해당 종목이 어느 시장에 포함되어 있는지 구하는 기능<br>
     * 서버와의 통신없이 메모리에 상주하는 값을 사용하므로 횟수제한 등은 없습니다.<br>
     *
     * @param code 종목 코드
     * @return {@link MarketKind}
     */
    public MarketKind getStockMarketKind(String code) {
        String data = getStockMarketKindRaw(code);
        return MarketKind.fromCode(data);
    }


    /**
     * Koava, Kw_의 초기화를 해제합니다.
     */
    public void uninitialize() {
        initialized = false;
        kw.kw_Uninitialize();
        eventHandlers.clear();
        Ole32.INSTANCE.CoUninitialize();
        Native.unregister(KwLibrary.class);
    }

    /**
     * 서버 접속 관련 이벤트 핸들러 지정 <br>
     * 참고 : {@link OnEventConnectCallback#invoke(int)}
     * @param callback 콜백
     */
    public void setOnEventConnect(OnEventConnectHandler callback) {
        baseEventHandler.onEventConnect = callback;
    }

    /**
     * 서버통신 후 데이터를 받은 시점을 알 수 있는 핸들러 지정 <br>
     * 참고 : {@link OnReceiveTrDataCallback#invoke(String, String, String, String, String, int, String, String, String)}
     *
     * @param callback 콜백
     */
    public void setOnReceiveTrData(OnReceiveTrDataHandler callback) {
        baseEventHandler.onReceiveTrData = callback;
    }

    /**
     * 실시간 데이터를 받으면 호출되는 핸들러 지정<br>
     * 참고 : {@link OnReceiveRealDataCallback#invoke(String, String, String)}
     *
     * @param callback 콜백
     */
    public void setOnReceiveRealData(OnReceiveRealDataHandler callback) {
        baseEventHandler.onReceiveRealData = callback;
    }

    /**
     * 서버통신 후 메시지를 받으면 실행되는 핸들러 지정<br>
     * 참고 : {@link OnReceiveMsgCallback#invoke(String, String, String, String)}
     * 
     * @param callback 콜백
     */
    public void setOnReceiveMsg(OnReceiveMsgHandler callback) {
        baseEventHandler.onReceiveMsg = callback;
    }

    /**
     * 체결데이터를 받으면 실행되는 핸들러 지정<br>
     * 참고 : {@link OnReceiveChejanDataCallback#invoke(String, int, String)}
     *
     * @param callback 콜백
     */
    public void setOnReceiveChejanData(OnReceiveChejanDataHandler callback) {
        baseEventHandler.onReceiveChejanData = callback;
    }

    /**
     * 조건검색 실시간 편입, 이탈 종목을 받을시 실행되는 핸들러 지정<br>
     * 참고 : {@link OnReceiveRealConditionCallback#invoke(String, String, String, String)}
     *
     * @param callback 콜백
     */
    public void setOnReceiveRealCondition(OnReceiveRealConditionHandler callback) {
        baseEventHandler.onReceiveRealCondition = callback;
    }

    /**
     * 조건검색 조회응답으로 종목리스트를 구분자(“;”)로 붙여서 받는 핸들러 지정<br>
     * 참고 : {@link OnReceiveTrConditionCallback#invoke(String, String, String, int, int)}
     *
     * @param callback 콜백
     */
    public void setOnReceiveTrCondition(OnReceiveTrConditionHandler callback) {
        baseEventHandler.onReceiveTrCondition = callback;
    }

    /**
     * 로컬에 사용자 조건식 저장 성공 여부를 확인하는 핸들러 지정 <br>
     * 참고 : {@link OnReceiveConditionVerCallback#invoke(int, String)}
     *
     * @param callback 콜백
     */
    public void setOnReceiveConditionVer(OnReceiveConditionVerHandler callback) {
        baseEventHandler.onReceiveConditionVer = callback;
    }

    /**
     * utf8 인코딩 사용 여부를 설정합니다.<br>
     * Koava는 초기화시 utf8 사용 여부를 true로 설정합니다.<br>
     * 만약 utf8을 사용 안 함으로 설정하시면, kw_에서 받는 String의 인코딩이 깨져 보입니다.
     *
     * @param useUtf8 true: 사용, false: 사용 안 함
     */
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

    /**
     * 현재 접속 상태를 (Open API로부터 받은 정보 그대로) 반환합니다.
     *
     * @return 0: 미연결, 1: 연결완료
     */
    public int getConnectStateRaw() {
        return kw.kw_GetConnectState();
    }

    /**
     * 현재 접속 상태를 반환합니다.
     *
     * @return 연결 완료 : {@link ConnectStateType#CONNECTED}, 미연결 : {@link ConnectStateType#DISCONNECTED}, 알 수 없음 : {@link ConnectStateType#UNKNOWN}
     */
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

    /**
     * 종목 코드의 한글명을 반환 (종목 한글 명)
     * @param code 종목 코드
     * @return 종목의 한글명
     */
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

    /**
     * 종목코드의 감리구분을 반환
     *
     * @param code 종목 코드
     * @return 정상, 투자주의, 투자경고, 투자위험, 투자주의환기종목
     */
    public String getMasterConstruction(String code) {
        Pointer p = kw.kw_GetMasterConstructionA(code);
        return getAStringAndFree(p);
    }

    /**
     * 종목코드의 상장일을 반환
     *
     * @param code 종목 코드
     * @return 상장일 (포맷: xxxxxxxx[8])
     */
    public String getMasterListedStockDateRaw(String code) {
        Pointer p = kw.kw_GetMasterListedStockDateA(code);
        return getAStringAndFree(p);
    }

    /**
     * 종목코드의 상장일을 반환
     *
     * @param code 종목 코드
     * @return 상장일
     */
    public LocalDate getMasterListedStockDate(String code) {
        String strDate =getMasterListedStockDateRaw(code);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(strDate, dtf);
    }

    /**
     * 종목코드의 전일가를 반환
     *
     * @param code 종목코드
     * @return 전일가
     */
    public String getMasterLastPrice(String code) {
        Pointer p = kw.kw_GetMasterLastPriceA(code);
        return getAStringAndFree(p);
    }

    /**
     * 종목코드의 종목 상태를 반환
     *
     * @param code 종목 코드
     * @return 종목 상태 - – 정상, 증거금100%, 거래정지, 관리종목, 감리종목, 투자유의종목, 담보대출, 액면분할, 신용가능
     */
    public String getMasterStockState(String code) {
        Pointer p = kw.kw_GetMasterStockStateA(code);
        return getAStringAndFree(p);
    }

    /**
     * 레코드의 반복 개수를 반환
     *
     * @param recordName 레코드명 (ex: "주식기본정보")
     * @return 레코드 반복개수
     */
    public int getDataCount(String recordName) {
        return kw.kw_GetDataCountA(recordName);
    }

    /**
     * 레코드의 반복순서와 아이템의 출력순서에 따라 수신데이터를 반환<br>
     * ex: {@code (“주식기본정보”, 0, 36)}
     *
     * @param recordName 레코드명
     * @param repeatIdx 반복순서
     * @param itemIdx 아이템 순서
     * @return 수신 데이터
     */
    public String getOutputValue(String recordName, int repeatIdx, int itemIdx) {
        Pointer p = kw.kw_GetOutputValueA(recordName, repeatIdx, itemIdx);
        return getAStringAndFree(p);
    }

    /**
     * 수신 데이터 반환<br>
     * ex: {@code (“OPT00001”, “주식기본정보”, 0, “현재가”)}
     *
     * @param trCode 코드
     * @param recordName 레코드명
     * @param index 복수데이터 인덱스
     * @param itemName 아이템명
     * @return 수신 데이터
     */
    public String getCommData(String trCode, String recordName, int index, String itemName) {
        Pointer p = kw.kw_GetCommDataA(trCode, recordName, index, itemName);
        return getAStringAndFree(p);
    }

    /**
     * 실시간 시세 데이터를 반환한다.<br>
     * FID.get()을 사용하셔도 됩니다.<br>
     * OnReceiveRealData()이벤트 안에서 사용해야 합니다.
     *
     * @param code 종목코드
     * @param fid 실시간 아이템
     * @return 수신 데이터
     */
    public String getCommRealData(String code, int fid) {
        Pointer p = kw.kw_GetCommRealDataA(code, fid);
        return getAStringAndFree(p);
    }

    /**
     * 실시간 시세 데이터를 반환한다.<br>
     * FID.get()을 사용하셔도 됩니다.<br>
     * OnReceiveRealData()이벤트 안에서 사용해야 합니다.
     *
     * @param code 종목코드
     * @param fid 실시간 아이템
     * @return 수신 데이터
     */
    public String getCommRealData(String code, RealTypes.FID fid) {
        return getCommRealData(code, fid.getId());
    }

    /**
     * 체결잔고 데이터를 반환한다<br>
     * OnReceiveChejan 이벤트 안에서 사용해야 합니다.
     *
     * @param fid 체결잔고 아이템
     * @return 수신데이터
     */
    public String getChejanData(int fid) {
        Pointer p = kw.kw_GetChejanDataA(fid);
        return getAStringAndFree(p);
    }

    /**
     * 체결잔고 데이터를 반환한다<br>
     * OnReceiveChejan 이벤트 안에서 사용해야 합니다.
     *
     * @param fid 체결잔고 아이템
     * @return 수신데이터
     */
    public String getChejanData(RealTypes.FID fid) {
        return getChejanData(fid.getId());
    }


    /**
     * OpenApi 모듈이 설치되어 있는 디렉토리를 구합니다.
     *
     * @return OpenApi 모듈이 설치된 경로
     */
    public String getAPIModulePath() {
        Pointer p = kw.kw_GetAPIModulePathA();
        return getAStringAndFree(p);
    }

    /**
     * 시장구분에 따른 종목코드를 반환
     *
     * @param market 시장 구분 0:장내, 3:ELW, 4:뮤추얼펀드, 5:신주인수권, 6:리츠, 8:ETF, 9:하이일드펀드, 10:코스닥, 30:K-OTC, 50:코넥스(KONEX)
     * @return 종목코드 리스트, 종목간 구분은 ";"
     */
    public String getCodeListByMarketRaw(String market) {
        Pointer p = kw.kw_GetCodeListByMarketA(market);
        return getAStringAndFree(p);
    }

    /**
     * 시장구분에 따른 종목코드를 반환
     *
     * @param market 시장 구분 0:장내, 3:ELW, 4:뮤추얼펀드, 5:신주인수권, 6:리츠, 8:ETF, 9:하이일드펀드, 10:코스닥, 30:K-OTC, 50:코넥스(KONEX)
     * @return 종목코드 리스트
     */
    public List<String> getCodeListByMarket(String market) {
        String data = getCodeListByMarketRaw(market);
        return KoavaUtils.strListToList(data);
    }

    /**
     * 지수선물 리스트를 반환
     *
     * @return 종목코드 리스트. ";" 으로 구분
     */
    public String getFutureListRaw() {
        Pointer p = kw.kw_GetFutureListA();
        return getAStringAndFree(p);
    }

    /**
     * 지수선물 리스트를 반환
     *
     * @return 종목코드 리스트
     */
    public List<String> getFutureList() {
        String data = getFutureListRaw();
        return KoavaUtils.strListToList(data);
    }

    /**
     * 지수옵션 행사가 리스트를 반환
     *
     * @return 행사가, ";"으로 구분
     */
    public String getActPriceListRaw() {
        Pointer p = kw.kw_GetActPriceListA();
        return getAStringAndFree(p);
    }

    /**
     * 지수옵션 행사가 리스트를 반환
     *
     * @return 행사가
     */
    public List<String> getActPriceList() {
        String data = getActPriceListRaw();
        return KoavaUtils.strListToList(data);
    }

    /**
     * 지수옵션 월물 리스트를 반환
     *
     * @return 월물, ";"으로 구분
     */
    public String getMonthListRaw() {
        Pointer p = kw.kw_GetMonthListA();
        return getAStringAndFree(p);
    }

    /**
     * 지수옵션 월물 리스트를 반환
     *
     * @return 월물 리스트
     */
    public List<String> getMonthList() {
        String data = getMonthListRaw();
        return KoavaUtils.strListToList(data);
    }

    /**
     *  행사가와 월물 콜풋으로 종목코드를 구합니다.<br>
     *  ex: {@code (“260.00”, OptionType.CALL, “201407”);}
     *
     * @param actPrice 행사가(소수점포함)
     * @param cp 콜풋구분
     * @param month 월물(6자리)
     * @return 종목코드
     */
    public String getOptionCode(String actPrice, OptionType cp, String month) {
        Pointer p = kw.kw_GetOptionCodeA(actPrice, cp.getValue(), month);
        return getAStringAndFree(p);
    }

    /**
     * 입력된 종목코드와 동일한 행사가의 코드중 입력한 월물의 코드를 구합니다.
     * ex: {@code (“201J7260”, OptionType.CALL, “201412”);}
     * 결과값 = 201JC260
     *
     * @param code 종목코드
     * @param cp 콜풋구분
     * @param month 월물(6자리)
     * @return 월물의 코드
     */
    public String getOptionCodeByMonth(String code, OptionType cp, String month) {
        Pointer p = kw.kw_GetOptionCodeByMonthA(code, cp.getValue(), month);
        return getAStringAndFree(p);
    }

    /**
     * 입력된 종목코드와 동일한 월물의 코드중 입력한 틱만큼 벌어진 코드를 구합니다.<br>
     * ex: {@code (“201J7260”, OptionType.CALL, -1);}
     * 결과값 = 201J7262
     *
     * @param code 종목코드
     * @param cp 콜풋구분
     * @param tick 행사가 틱
     * @return 종목코드
     */
    public String getOptionCodeByActPrice(String code, OptionType cp, int tick) {
        Pointer p = kw.kw_GetOptionCodeByActPriceA(code, cp.getValue(), tick);
        return getAStringAndFree(p);
    }

    /**
     * 주식선물 코드 리스트를 반환합니다.
     *
     * @param baseAssetCode 기초자산코드
     * @return 종목코드 리스트, ";"으로 구분
     */
    public String getSFutureListRaw(String baseAssetCode) {
        Pointer p = kw.kw_GetSFutureListA(baseAssetCode);
        return getAStringAndFree(p);
    }

    /**
     * 주식선물 코드 리스트를 반환합니다.
     *
     * @param baseAssetCode 기초자산코드
     * @return 종목코드 리스트
     */
    public List<String> getSFutureList(String baseAssetCode) {
        String data = getSFutureListRaw(baseAssetCode);
        return KoavaUtils.strListToList(baseAssetCode);
    }

    /**
     * 주식선물 코드를 반환합니다.<br>
     * ex: {@code (“11”, 0);}
     *
     * @param baseAssetCode 기초자산코드
     * @param index 0~3 지수선물코드, 4~7 지수스프레드, 8~11 스타 선물, 12~ 스타 스프레드
     * @return 종목코드
     */
    public String getSFutureCodeByIndex(String baseAssetCode, int index) {
        Pointer p = kw.kw_GetSFutureCodeByIndexA(baseAssetCode, index);
        return getAStringAndFree(p);
    }

    /**
     * 주식옵션 행사가 리스트를 반환합니다.<br>
     * ex: {@code (“11”);}
     *
     * @param baseAssetGb 기초자산코드구분
     * @return 행사가 리스트, 행사가간 구분은 ";"
     */
    public String getSActPriceListRaw(String baseAssetGb) {
        Pointer p = kw.kw_GetSActPriceListA(baseAssetGb);
        return getAStringAndFree(p);
    }
    /**
     * 주식옵션 행사가 리스트를 반환합니다.<br>
     * ex: {@code (“11”);}
     *
     * @param baseAssetGb 기초자산코드구분
     * @return 행사가 리스트
     */
    public List<String> getSActPriceList(String baseAssetGb) {
        String data =getSActPriceListRaw(baseAssetGb);
        return KoavaUtils.strListToList(data);
    }

    /**
     * 주식옵션 월물 리스트를 반환한다.<br>
     * ex: {@code ("11");}
     *
     * @param baseAssetGb 기초자산코드구분
     * @return 월물 리스트, 월물간 구분은 ‘;’
     */
    public String getSMonthListRaw(String baseAssetGb) {
        Pointer p = kw.kw_GetSMonthListA(baseAssetGb);
        return getAStringAndFree(p);
    }

    /**
     * 주식옵션 월물 리스트를 반환한다.<br>
     * ex: {@code ("11");}
     *
     * @param baseAssetGb 기초자산코드구분
     * @return 월물 리스트
     */
    public List<String> getSMonthList(String baseAssetGb) {
        String data =getSMonthListRaw(baseAssetGb);
        return KoavaUtils.strListToList(data);
    }

    /**
     * 주식옵션 코드를 반환한다.<br>
     *
     * @param baseAssetGb 기초자산코드구분
     * @param actPrice 행사가
     * @param cp 콜풋구분
     * @param month 월물
     * @return 주식옵션 코드
     */
    public String getSOptionCode(String baseAssetGb, String actPrice, OptionType cp, String month) {
        Pointer p = kw.kw_GetSOptionCodeA(baseAssetGb, actPrice, cp.getValue(), month);
        return getAStringAndFree(p);
    }


    /**
     * 입력한 주식옵션 코드에서 월물만 변경하여 반환한다.
     *
     * @param strBaseAssetGb 기초자산코드구분
     * @param code 종목코드
     * @param cp 콜풋구분
     * @param month 월물
     * @return 주식옵션 코드
     */
    public String getSOptionCodeByMonth(String strBaseAssetGb, String code, OptionType cp, String month) {
        Pointer p = kw.kw_GetSOptionCodeByMonthA(strBaseAssetGb, code, cp.getValue(), month);
        return getAStringAndFree(p);
    }

    /**
     * 입력한 주식옵션 코드에서 행사가만 변경하여 반환한다.
     *
     * @param baseAssetGb 기초자산코드구분
     * @param code 종목코드
     * @param cp 콜풋구분
     * @param tick 행사가 틱
     * @return 주식옵션 코드
     */
    public String getSOptionCodeByActPrice(String baseAssetGb, String code, OptionType cp, int tick) {
        Pointer p = kw.kw_GetSOptionCodeByActPriceA(baseAssetGb, code, cp.getValue(), tick);
        return getAStringAndFree(p);
    }

    /**
     * 지수선물 코드를 반환한다.
     *
     * @param index 0~3 지수선물코드, 4~7 지수스프레드
     * @return 종목코드
     */
    public String getFutureCodeByIndex(int index) {
        Pointer p = kw.kw_GetFutureCodeByIndexA(index);
        return getAStringAndFree(p);
    }

    /**
     * 테마코드와 테마명을 반환한다.
     *
     * @param type 정렬순서
     * @return 코드와 코드명 리스트
     */
    public String getThemeGroupList(ThemeGroupListSortingType type) {
        Pointer p = kw.kw_GetThemeGroupListA(type.getTypeCode());
        return getAStringAndFree(p);
    }

    /**
     * 테마코드에 소속된 종목코드를 반환한다.
     *
     * @param themeCode 테마코드
     * @return 종목코드 리스트, ";"으로 구분
     */
    public String getThemeGroupCodeRaw(String themeCode) {
        Pointer p = kw.kw_GetThemeGroupCodeA(themeCode);
        return getAStringAndFree(p);
    }

    /**
     * 테마코드에 소속된 종목코드를 반환한다.
     *
     * @param themeCode 테마코드
     * @return 종목코드 리스트
     */
    public List<String> getThemeGroupCode(String themeCode) {
        String data = getThemeGroupCodeRaw(themeCode);
        return KoavaUtils.strListToList(data);
    }

    /**
     * 주식선옵 기초자산코드/종목명을 반환한다.
     *
     * @return 기초자산코드/종목명, 코드와 종목명 구분은 "|" 코드간 구분은";" <br>
     * ex) 211J8045|삼성전자 C 201408;212J8009|SK텔레콤 C 201408
     */
    public String getSFOBasisAssetListRaw() {
        Pointer p = kw.kw_GetSFOBasisAssetListA();
        return getAStringAndFree(p);
    }

    /**
     * 주식선옵 기초자산코드/종목명을 반환한다.
     *
     * @return 기초자산코드/종목명, 코드와 종목명 구분은 "|" 코드간 구분은";"
     */
    public List<BasisAssetCodeAndStockName> getSFOBasisAssetList() {
        String data = getSFOBasisAssetListRaw();
        List<String> dataList = KoavaUtils.strListToList(data);

        ArrayList<BasisAssetCodeAndStockName> ret = new ArrayList<>();
        for (String s : dataList) {
            String[] tokens = s.split("\\|");
            if (tokens.length != 2) {
                System.out.println("잘못된 SFOBasisAssetList 요소 : " + s);
                continue;
            }

            String code = tokens[0];
            String name = tokens[1];
            ret.add(new BasisAssetCodeAndStockName(code, name));
        }

        return ret;
    }

    /**
     * 지수옵션 ATM을 반환한다
     *
     * @return ATM
     */
    public String getOptionATM() {
        Pointer p = kw.kw_GetOptionATMA();
        return getAStringAndFree(p);
    }

    /**
     * 주식옵션 ATM을 반환한다.
     *
     * @param baseAssetGb 기초자산구분
     * @return ATM
     */
    public String getSOptionATM(String baseAssetGb) {
        Pointer p = kw.kw_GetSOptionATMA(baseAssetGb);
        return getAStringAndFree(p);
    }

    /**
     * 회원사 코드와 이름을 반환합니다.
     * @return 회원사코드|회원사명;회원사코드|회원사명;…
     */
    public String getBranchCodeName() {
        Pointer pointer = kw.kw_GetBranchCodeNameA();
        return getAStringAndFree(pointer);
    }

    /**
     *  신용주식 주문을 서버로 전송한다.<br>
     * 신용매수 주문<br>
     * - 신용구분값 “03”, 대출일은 “공백”<br><br>
     * 신용매도 융자상환 주문<br>
     * - 신용구분값 “33”, 대출일은 종목별 대출일 입력<br>
     * - OPW00005/OPW00004 TR조회로 대출일 조회<br><br>
     * 신용매도 융자합 주문시<br>
     * - 신용구분값 “99”, 대출일은 “99991231”<br>
     * - 단 신용잔고 5개까지만 융자합 주문가능<br><br>
     * 나머지 입력값은 {@link Koava#sendOrder(String, String, String, int, String, int, int, HogaType, String)}함수 설명참고
     *
     * @param rqName 사용자 구분 요청 명
     * @param screenNo 화면번호[4]
     * @param accNo 계좌번호[10]
     * @param orderType 주문 유형  (1:신규매수, 2:신규매도, 3:매수취소, 4:매도취소, 5:매수정정, 6:매도정정)
     * @param code 주식 종목 코드
     * @param qty 주문 수량
     * @param price 주문 단가
     * @param hogaGb 거래 구분
     * @param creditGb 신용구분 (신용매수:03, 신용매도 융자상환:33,신용매도 융자합:99)
     * @param loanDate 대출일
     * @param orgOrderNo 원주문번호
     * @return 에러코드
     */
    public KoaCode sendOrderCredit(String rqName, String screenNo, String accNo, int orderType, String code, int qty, int price, HogaType hogaGb, String creditGb, String loanDate, String orgOrderNo) {
        return new KoaCode(kw.kw_SendOrderCreditA(rqName, screenNo, accNo, orderType, code, qty, price, hogaGb.getCode(), creditGb, loanDate, orgOrderNo));
    }

    /**
     *  신용주식 주문을 서버로 전송한다.<br>
     * 신용매수 주문<br>
     * - 신용구분값 “03”, 대출일은 “공백”<br><br>
     * 신용매도 융자상환 주문<br>
     * - 신용구분값 “33”, 대출일은 종목별 대출일 입력<br>
     * - OPW00005/OPW00004 TR조회로 대출일 조회<br><br>
     * 신용매도 융자합 주문시<br>
     * - 신용구분값 “99”, 대출일은 “99991231”<br>
     * - 단 신용잔고 5개까지만 융자합 주문가능<br><br>
     * 나머지 입력값은 {@link Koava#sendOrder(String, String, String, int, String, int, int, HogaType, String)}함수 설명참고
     *
     * @param rqName 사용자 구분 요청 명
     * @param screenNo 화면번호[4]
     * @param accNo 계좌번호[10]
     * @param orderType 주문 유형
     * @param code 주식 종목 코드
     * @param qty 주문 수량
     * @param price 주문 단가
     * @param hogaGb 거래 구분
     * @param creditGb 신용구분 (신용매수:03, 신용매도 융자상환:33,신용매도 융자합:99)
     * @param loanDate 대출일
     * @param orgOrderNo 원주문번호
     * @return 에러코드
     */
    public KoaCode sendOrderCredit(String rqName, String screenNo, String accNo, OrderType orderType, String code, int qty, int price, HogaType hogaGb, String creditGb, String loanDate, String orgOrderNo) {
        return sendOrderCredit(rqName, screenNo, accNo, orderType.getCode(), code, qty, price, hogaGb, creditGb, loanDate, orgOrderNo);
    }

    /**
     * OpenAPI기본 기능외에 기능을 사용하기 쉽도록 도와주는 함수.<br>
     * 특수 함수를 실행시킬 수 있다.
     *
     * @param functionName  함수이름 혹은 기능이름
     * @param param  함수 매개변수
     * @return 실행한 함수의 함수 반환값
     */
    public String koaFunctions(String functionName, String param) {
        Pointer p = kw.kw_KOA_FunctionsA(functionName, param);
        return getAStringAndFree(p);
    }

    /**
     * 다수의 아이디로 자동로그인이 필요할 때 사용한다.
     * @param infoData 아이디
     * @return 통신결과
     */
    public int setInfoData(String infoData) {
        return kw.kw_SetInfoDataA(infoData);
    }

    /**
     * 실시간 등록을 한다.<br>
     * strRealType이 “0” 으로 하면 같은화면에서 다른종목 코드로 실시간 등록을 하게 되면 마지막
     * 에 사용한 종목코드만 실시간 등록이 되고 기존에 있던 종목은 실시간이 자동 해지됨.
     * “1”로 하면 같은화면에서 다른 종목들을 추가하게 되면 기존에 등록한 종목도 함께 실시간 시세
     * 를 받을 수 있음.
     * 꼭 같은 화면이여야 하고 최초 실시간 등록은 “0”으로 하고 이후부터 “1”로 등록해야함.
     *
     * @param screenNo 화면 번호
     * @param codeList 실시간 등록할 종목코드(복수종목가능 – “종목1;종목2;종목3;….”)
     * @param fidList  실시간 등록할 FID(“FID1;FID2;FID3;…..”)
     * @param optType “0”(초기화), “1”(추가) 타입
     * @return 통신결과
     */
    public int setRealReg(String screenNo, String codeList, String fidList, String optType) {
        return kw.kw_SetRealRegA(screenNo, codeList, fidList, optType);
    }

    /**
     * 실시간 등록을 한다.<br>
     * strRealType이 “0” 으로 하면 같은화면에서 다른종목 코드로 실시간 등록을 하게 되면 마지막
     * 에 사용한 종목코드만 실시간 등록이 되고 기존에 있던 종목은 실시간이 자동 해지됨.
     * “1”로 하면 같은화면에서 다른 종목들을 추가하게 되면 기존에 등록한 종목도 함께 실시간 시세
     * 를 받을 수 있음.
     * 꼭 같은 화면이여야 하고 최초 실시간 등록은 “0”으로 하고 이후부터 “1”로 등록해야함.
     *
     * @param screenNo 화면 번호
     * @param codeList 실시간 등록할 종목코드(복수종목가능 – “종목1;종목2;종목3;….”)
     * @param fidList  실시간 등록할 FID(“FID1;FID2;FID3;…..”)
     * @param option 옵션
     * @return 통신결과
     */
    public int setRealReg(String screenNo, String codeList, String fidList, RealRegistOption option) {
        return setRealReg(screenNo, codeList, fidList, option == RealRegistOption.KEEP ? "1" : "0");
    }

    /**
     * 실시간 등록을 한다.<br>
     * strRealType이 “0” 으로 하면 같은화면에서 다른종목 코드로 실시간 등록을 하게 되면 마지막
     * 에 사용한 종목코드만 실시간 등록이 되고 기존에 있던 종목은 실시간이 자동 해지됨.
     * “1”로 하면 같은화면에서 다른 종목들을 추가하게 되면 기존에 등록한 종목도 함께 실시간 시세
     * 를 받을 수 있음.
     * 꼭 같은 화면이여야 하고 최초 실시간 등록은 “0”으로 하고 이후부터 “1”로 등록해야함.
     *
     * @param screenNo 화면 번호
     * @param codeList 실시간 등록할 종목코드(복수종목가능 – “종목1;종목2;종목3;….”)
     * @param fid  실시간 등록할 FID
     * @param option 옵션
     * @return 통신결과
     */
    public int setRealReg(String screenNo, String codeList, RealTypes.FID fid, RealRegistOption option) {
        return setRealReg(screenNo, codeList, Integer.toString(fid.getId()), option == RealRegistOption.KEEP ? "1" : "0");
    }

    /**
     * 실시간 등록을 한다.<br>
     * strRealType이 “0” 으로 하면 같은화면에서 다른종목 코드로 실시간 등록을 하게 되면 마지막
     * 에 사용한 종목코드만 실시간 등록이 되고 기존에 있던 종목은 실시간이 자동 해지됨.
     * “1”로 하면 같은화면에서 다른 종목들을 추가하게 되면 기존에 등록한 종목도 함께 실시간 시세
     * 를 받을 수 있음.
     * 꼭 같은 화면이여야 하고 최초 실시간 등록은 “0”으로 하고 이후부터 “1”로 등록해야함.
     *
     * @param screenNo 화면 번호
     * @param codeList 실시간 등록할 종목코드(복수종목가능 – “종목1;종목2;종목3;….”)
     * @param fidIterable  실시간 등록할 FID Iterable
     * @param option 옵션
     * @return 통신결과
     */
    public int setRealReg(String screenNo, String codeList, Iterable<RealTypes.FID> fidIterable, RealRegistOption option) {
        String sList = KoavaUtils.fidIterableToStrList(fidIterable);
        return setRealReg(screenNo, codeList, sList, option);
    }

    /**
     * 실시간 등록을 한다.<br>
     * strRealType이 “0” 으로 하면 같은화면에서 다른종목 코드로 실시간 등록을 하게 되면 마지막
     * 에 사용한 종목코드만 실시간 등록이 되고 기존에 있던 종목은 실시간이 자동 해지됨.
     * “1”로 하면 같은화면에서 다른 종목들을 추가하게 되면 기존에 등록한 종목도 함께 실시간 시세
     * 를 받을 수 있음.
     * 꼭 같은 화면이여야 하고 최초 실시간 등록은 “0”으로 하고 이후부터 “1”로 등록해야함.
     *
     * @param screenNo 화면 번호
     * @param codeList 실시간 등록할 종목코드(복수종목가능 – “종목1;종목2;종목3;….”)
     * @param fidList  실시간 등록할 FID 리스트
     * @param option 옵션
     * @return 통신결과
     */
    public int setRealReg(String screenNo, String codeList, List<RealTypes.FID> fidList, RealRegistOption option) {
        String sList = KoavaUtils.fidListToStrList(fidList);
        return setRealReg(screenNo, codeList, sList, option);
    }

    /**
     * 실시간 등록을 한다.<br>
     * strRealType이 “0” 으로 하면 같은화면에서 다른종목 코드로 실시간 등록을 하게 되면 마지막
     * 에 사용한 종목코드만 실시간 등록이 되고 기존에 있던 종목은 실시간이 자동 해지됨.
     * “1”로 하면 같은화면에서 다른 종목들을 추가하게 되면 기존에 등록한 종목도 함께 실시간 시세
     * 를 받을 수 있음.
     * 꼭 같은 화면이여야 하고 최초 실시간 등록은 “0”으로 하고 이후부터 “1”로 등록해야함.
     *
     * @param screenNo 화면 번호
     * @param codeList 실시간 등록할 종목코드(복수종목가능 – “종목1;종목2;종목3;….”)
     * @param fidList  실시간 등록할 FID 배열
     * @param option 옵션
     * @return 통신결과
     */
    public int setRealReg(String screenNo, String codeList, RealTypes.FID[] fidList, RealRegistOption option) {
        String sList = KoavaUtils.fidListToStrList(fidList);
        return setRealReg(screenNo, codeList, sList, option);
    }

    /**
     * 서버에 저장된 사용자 조건식을 조회해서 임시로 파일에 저장<br>
     * System 폴더에 아이디_NewSaveIndex.dat파일로 저장된다. Ocx가 종료되면 삭제시킨다.
     * 조건검색 사용시 이함수를 최소 한번은 호출해야 조건검색을 할 수 있다.
     * 영웅문에서 사용자 조건을 수정 및 추가하였을 경우에도 최신의 사용자 조건을 받고 싶으면 다시
     * 조회해야한다
     *
     * @return ?
     */
    public int getConditionLoad() {
        return kw.kw_GetConditionLoad();
    }

    /**
     * 조건검색 조건명 리스트를 받아온다.
     *
     * @return 조건명 리스트를 구분(“;”)하여 받아온다. Ex) 인덱스1^조건명1;인덱스2^조건명2;인덱스3^조건명3;…
     */
    public String getConditionNameListRaw() {
        Pointer p = kw.kw_GetConditionNameListA();
        return getAStringAndFree(p);
    }

    /**
     * 조건검색 조건명 리스트를 받아온다.
     *
     * @return 조건명 리스트
     */
    public List<CondIdxAndName> getConditionNameList() {
        String data = getConditionNameListRaw();
        List<String> list = KoavaUtils.strListToList(data);

        if (list.isEmpty()) return Collections.emptyList();
        ArrayList<CondIdxAndName> ret = new ArrayList<>();

        for (String cond : list) {
            String[] tokens = cond.split("\\^");
            if (tokens.length != 2) {
                System.out.println("잘못된 데이터 : " + cond);
                continue;
            }

            String index = tokens[0];
            String condName = tokens[1];

            CondIdxAndName condPair = new CondIdxAndName(index, condName);
            ret.add(condPair);
        }

        return ret;
    }

    /**
     *  조건검색 종목조회TR송신한다.<br>
     * 단순 조건식에 맞는 종목을 조회하기 위해서는 조회구분을 0으로 하고,<br>
     * 실시간 조건검색을 하기 위해서는 조회구분을 1로 한다.<br>
     * OnReceiveTrCondition으로 결과값이 온다.<br>
     * 연속조회가 필요한 경우에는 응답받는 곳에서 연속조회 여부에 따라 연속조회를 송신하면된다<br>
     *
     * @param scrNo 화면 번호
     * @param conditionName 조건명
     * @param index 조거념인덱스
     * @param search 조회구분(0:일반조회, 1:실시간조회, 2:연속조회)
     * @return 성공 1, 실패 0
     */
    public int sendCondition(String scrNo, String conditionName, int index, int search) {
        return kw.kw_SendConditionA(scrNo, conditionName, index, search);
    }

    /**
     *  조건검색 실시간 중지TR을 송신한다.<br>
     * 해당 조건명의 실시간 조건검색을 중지하거나, <br>
     * 다른 조건명으로 바꿀 때 이전 조건명으로 실시간 조건검색을 반드시 중지해야한다. <br>
     * 화면 종료시에도 실시간 조건검색을 한 조건명으로 전부 중지해줘야 한다. <br>
     *
     * @param scrNo 화면 번호
     * @param conditionName 조건명
     * @param index 조건명인덱스
     */
    public void sendConditionStop(String scrNo, String conditionName, int index) {
        kw.kw_SendConditionStopA(scrNo, conditionName, index);
    }

    public Variant.VARIANT getCommDataEx(String trCode, String recordName) {
        return kw.kw_GetCommDataExA(trCode, recordName);
    }

    /**
     *  종목별 실시간 해제.<br>
     *  {@link Koava#setRealReg} 함수로 실시간 등록한 종목만 실시간 해제 할 수 있다.
     *
     * @param scrNo 실시간 해제할 화면 번호
     * @param delCode 실시간 해제할 종목.
     */
    public void setRealRemove(String scrNo, String delCode) {
        kw.kw_SetRealRemoveA(scrNo, delCode);
    }

    public int getMarketType(String trCode) {
        return kw.kw_GetMarketTypeA(trCode);
    }

    /**
     * Tran을 서버로 송신 <br>
     * 예시: {@code ( “RQ_1”, “OPT00001”, 0, “0101”);}
     *
     * @param rqName 사용자구분 명(원하는 값 입력))
     * @param trCode Tran명 입력
     * @param prevNext 0: 조회, 2: 연속
     * @param screenNo 4자리의 화면 번호
     * @return Koa 응답 코드
     */
    public KoaCode commRqData(String rqName, String trCode, int prevNext, String screenNo) {
        return new KoaCode(kw.kw_CommRqDataA(rqName, trCode, prevNext, screenNo));
    }

    /**
     * 로그인한 사용자 정보를 반환
     *
     * @param tag 어떤 정보를 가져올지 지정합니다
     * @return 결과값을 "문자열"로 반환합니다.
     */
    public String getLoginInfo(LoginInfoTag tag) {
        Pointer pointer = kw.kw_GetLoginInfoA(tag.name());
        return getAStringAndFree(pointer);
    }

    /**
     * 계좌 목록 조회
     * @return 계좌 리스트
     */
    public List<String> getAccountNoList() {
        String data = getLoginInfo(LoginInfoTag.ACCNO);
        return KoavaUtils.strListToList(data);
    }

    /**
     * 주식 주문을 서버로 전송<br>
     * ※ 시장가, 최유리지정가, 최우선지정가, 시장가IOC, 최유리IOC, 시장가FOK, 최유리FOK, 장전시간외, 장후시간외 주문시 주문가격을 입력하지 않습니다. <br>
     * 예시 : {@code sendOrder(“RQ_1”, “0101”, “5015123410”, 1, “000660”, 10, 0, hogaGb, “”);}
     *
     * @param rqName 사용자 구분 요청 명 (나중에 데이터 받았을 때 구분하기 위함)
     * @param screenNo 4자리 화면 번호
     * @param accNo 10자리 계좌 번호
     * @param orderType 주문 유형 (1:신규매수, 2:신규매도, 3:매수취소, 4:매도취소, 5:매수정정, 6:매도정정)
     * @param code 주식 종목 코드
     * @param qty 주문 수량
     * @param price 주문 단가
     * @param hogaGb 거래 구분
     * @param orgOrderNo 원 주문 번호
     * @return 결과 코드
     */
    public KoaCode sendOrder(String rqName, String screenNo, String accNo, int orderType, String code, int qty, int price, HogaType hogaGb, String orgOrderNo) {
        return new KoaCode(kw.kw_SendOrderA(rqName, screenNo, accNo, orderType, code, qty, price, hogaGb.getCode(), orgOrderNo));
    }

    /**
     * 주식 주문을 서버로 전송<br>
     * ※ 시장가, 최유리지정가, 최우선지정가, 시장가IOC, 최유리IOC, 시장가FOK, 최유리FOK, 장전시간외, 장후시간외 주문시 주문가격을 입력하지 않습니다. <br>
     * 예시 : {@code sendOrder(“RQ_1”, “0101”, “5015123410”, 1, “000660”, 10, 0, hogaGb, “”);}
     *
     * @param rqName 사용자 구분 요청 명 (나중에 데이터 받았을 때 구분하기 위함)
     * @param screenNo 4자리 화면 번호
     * @param accNo 10자리 계좌 번호
     * @param orderType 주문 유형
     * @param code 주식 종목 코드
     * @param qty 주문 수량
     * @param price 주문 단가
     * @param hogaGb 거래 구분
     * @param orgOrderNo 원 주문 번호
     * @return 결과 코드
     */
    public KoaCode sendOrder(String rqName, String screenNo, String accNo, OrderType orderType, String code, int qty, int price, HogaType hogaGb, String orgOrderNo) {
        return sendOrder(rqName, screenNo, accNo, orderType.getCode(), code, qty, price, hogaGb, orgOrderNo);
    }

    public KoaCode sendOrderFO(String rqName, String screenNo, String accNo, String code, int ordKind, String slbyTp, String ordTp, int qty, String price, String orgOrdNo) {
        return new KoaCode(kw.kw_SendOrderFOA(rqName, screenNo, accNo, code, ordKind, slbyTp, ordTp, qty, price, orgOrdNo));
    }

    /**
     * Tran 입력 값을 서버통신 전에 입력<br>
     * ex) {@code (“계좌번호”, “5015123401”);}
     *
     * @param id 아이템 명
     * @param value 입력 값
     */
    public void setInputValue(String id, String value) {
        kw.kw_SetInputValueA(id, value);
    }

    /**
     * 화면 내 모든 리얼데이터 요청을 제거<br>
     * <b>화면을 종료할 때 반드시 위 함수를 호출해야 함</b><br>
     * ex) {@code (“0101”);}
     *
     * @param scnNo 화면 번호
     */
    public void disconnectRealData(String scnNo) {
        kw.kw_DisconnectRealDataA(scnNo);
    }

    /**
     * 레코드 반복횟수를 반환
     *
     * @param code Tran 명 (ex: "OPT00001")
     * @param recordName 레코드 명 (ex: "주식기본정보")
     * @return 레코드 반복 횟수
     */
    public int getRepeatCnt(String code, String recordName) {
        return kw.kw_GetRepeatCntA(code, recordName);
    }

    /**
     * 복수종목조회 Tran을 서버로 송신
     *
     * @param arrCode 종목 코드 리스트. ";"으로 종목 구분
     * @param next 연속 조회 여부
     * @param codeCount 종목 개수
     * @param typeFlag 조회 구분
     * @param rqName 사용자구분 명
     * @param screenNo 4자리 화면번호
     * @return 응답 코드
     */
    public KoaCode commKwRqData(String arrCode, boolean next, int codeCount, int typeFlag, String rqName, String screenNo) {
        return new KoaCode(kw.kw_CommKwRqDataA(arrCode, next ? 1 : 0, codeCount, typeFlag, rqName, screenNo));
    }

    /**
     * 복수종목조회 Tran을 서버로 송신
     *
     * @param codeList 종목 코드 리스트
     * @param next 연속 조회 여부
     * @param codeCount 종목 개수
     * @param typeFlag 조회 구분
     * @param rqName 사용자구분 명
     * @param screenNo 4자리 화면번호
     * @return 응답 코드
     */
    public KoaCode commKwRqData(List<String> codeList, boolean next, int codeCount, int typeFlag, String rqName, String screenNo) {
        StringBuilder sb = new StringBuilder();
        int listSize = codeList.size();

        for (int i = 0; i < listSize; i++) {
            if (i != 0) sb.append(";");
            sb.append(codeList.get(i));
        }

        return commKwRqData(sb.toString(), next, codeCount, typeFlag, rqName, screenNo);
    }

    /**
     * 복수종목조회 Tran을 서버로 송신
     *
     * @param codeArr 종목 코드 배열
     * @param next 연속 조회 여부
     * @param codeCount 종목 개수
     * @param typeFlag 조회 구분
     * @param rqName 사용자구분 명
     * @param screenNo 4자리 화면번호
     * @return 응답 코드
     */
    public KoaCode commKwRqData(String[] codeArr, boolean next, int codeCount, int typeFlag, String rqName, String screenNo) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < codeArr.length; i++) {
            if (i != 0) sb.append(";");
            sb.append(codeArr[i]);
        }

        return commKwRqData(sb.toString(), next, codeCount, typeFlag, rqName, screenNo);
    }

    /**
     * 연결이 종료될 때까지 기다립니다. (코드 흐름 멈춤)
     *
     * @see Koava#disconnect()
     */
    public void waitDisconnection() {
        kw.kw_Wait();
    }

    /**
     * kw_의 sleep 함수 호출
     *
     * @param msec 밀리 초
     */
    public void sleep(int msec) {
        kw.kw_Sleep(msec);
    }

    /**
     * Open API 연결 종료
     */
    public void disconnect() {
        kw.kw_Disconnect();
    }


    /**
     * Kw_ 및 Open API에서 생성된 문자열 포인터를 받아
     * 자바의 문자열 객체로 만들고 포인터가 가리키는 메모리를 해제합니다.
     *
     * @param p kw_ 에서 받은 문자열 포인터
     * @return 포인터가 가리키던 문자열
     */
    private String getAStringAndFree(Pointer p) {
        String ret = p.getString(0);
        kw.kw_FreeStringA(p);
        return ret;
    }

    private void putDataToWaiters(Class targetWaiter, EventData data) {
        if (!KoavaWaiter.waiterList.isEmpty()) {
            synchronized (KoavaWaiter.waiterList) {
                for (KoavaWaiter koavaWaiter : KoavaWaiter.waiterList) {
                    if (!(koavaWaiter.getClass().equals(targetWaiter))) continue;

                    if (koavaWaiter.checkFilter(data)) {
                        koavaWaiter.setData(data);
                    }
                }
            }
        }
    }

    /**
     * 모든 이벤트 핸들러 및 setOn... 메소드들의 콜백들에게 이벤트를 넘겨줄 콜백을 설정합니다.<br>
     * 이에 대해 더 좋은 아이디어를 이슈에 남겨주시면 정말 감사하겠습니다...<br>
     * 변수에 콜백을 저장하는 이유는, GC에 의해 콜백이 제거되는 것을 방지하기 위함입니다.
     */
    private void initEventHandler() {
        onEventConnect = errCode -> {
            EventConnectData data = new EventConnectData(errCode);
            putDataToWaiters(EventConnectWaiter.class, data);

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onEventConnect(data);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveTrData = (scrNo, rqName, trCode, recordName, prevNext, dataLength, errorCode, message, splmMsg) -> {
            TrData data = new TrData(scrNo, rqName, trCode, recordName, prevNext, dataLength, errorCode, message, splmMsg);
            putDataToWaiters(TrDataWaiter.class, data);

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveTrData(data);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveRealData = (realKey, realType, realData) -> {
            this.realKey = realKey;
            RealData data = new RealData(realKey, realType, realData);
            putDataToWaiters(RealDataWaiter.class, data);

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveRealData(data);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveMsg = (OnReceiveMsgCallback) (scrNo, rqName, trCode, msg) -> {
            MsgData data = new MsgData(scrNo, rqName, trCode, msg);
            putDataToWaiters(MsgWaiter.class, data);

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveMsg(data);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveChejanData = (OnReceiveChejanDataCallback) (gubun, itemCnt, fIdList) -> {
            ChejanData data = new ChejanData(gubun, itemCnt, fIdList);
            putDataToWaiters(ChejanDataWaiter.class, data);

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveChejanData(data);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveRealCondition = (OnReceiveRealConditionCallback) (trCode, type, conditionName, conditionIndex) -> {
            RealConditionData data = new RealConditionData(trCode, type, conditionName, conditionIndex);
            putDataToWaiters(RealConditionWaiter.class, data);

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveRealCondition(data);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveTrCondition = (OnReceiveTrConditionCallback) (scrNo, codeList, conditionName, index, next) -> {
            TrConditionData data = new TrConditionData(scrNo, codeList, conditionName, index, next);
            putDataToWaiters(TrConditionWaiter.class, data);

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveTrCondition(data);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        };

        onReceiveConditionVer = (OnReceiveConditionVerCallback) (ret, msg) -> {
            ConditionVerData data = new ConditionVerData(ret, msg);
            putDataToWaiters(ConditionVerWaiter.class, data);

            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveConditionVer(data);
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

/**
 * setOn... 메소드들을 통해 설정된 콜백은 이 이벤트 핸들러가 실행합니다.
 */
class BaseEventHandler extends KoaEventHandler {
    OnEventConnectHandler onEventConnect;
    OnReceiveTrDataHandler onReceiveTrData;
    OnReceiveRealDataHandler onReceiveRealData;
    OnReceiveMsgHandler onReceiveMsg;
    OnReceiveChejanDataHandler onReceiveChejanData;
    OnReceiveRealConditionHandler onReceiveRealCondition;
    OnReceiveTrConditionHandler onReceiveTrCondition;
    OnReceiveConditionVerHandler onReceiveConditionVer;

    @Override
    public void onEventConnect(EventConnectData data) {
        if (onEventConnect == null) return;
        onEventConnect.handle(data);
    }

    @Override
    public void onReceiveChejanData(ChejanData data) {
        if (onReceiveChejanData == null) return;
        onReceiveChejanData.handle(data);
    }

    @Override
    public void onReceiveConditionVer(ConditionVerData data) {
        if (onReceiveConditionVer == null) return;
        onReceiveConditionVer.handle(data);
    }

    @Override
    public void onReceiveMsg(MsgData data) {
        if (onReceiveMsg == null) return;
        onReceiveMsg.handle(data);
    }

    @Override
    public void onReceiveRealCondition(RealConditionData data) {
        if (onReceiveRealCondition == null) return;
        onReceiveRealCondition.handle(data);
    }

    @Override
    public void onReceiveRealData(RealData data) {
        if (onReceiveRealData == null) return;
        onReceiveRealData.handle(data);
    }

    @Override
    public void onReceiveTrCondition(TrConditionData data) {
        if (onReceiveTrCondition == null) return;
        onReceiveTrCondition.handle(data);
    }

    @Override
    public void onReceiveTrData(TrData data) {
        if (onReceiveTrData == null) return;
        onReceiveTrData.handle(data);
    }
}
