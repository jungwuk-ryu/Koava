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
import me.jungwuk.koava.interfaces.KwLibrary;
import me.jungwuk.koava.utils.KoavaUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Koava {
    private KwLibrary kw;
    private BaseEventHandler baseEventHandler;
    private final ArrayList<KoaEventHandler> eventHandlers = new ArrayList<>();

    private boolean initialized = false;

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

        RealTypes.getFidByNum(10); // 캐시를 생성하도록 유도합니다.

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

    public String getChejanData(int fid) {
        Pointer p = kw.kw_GetChejanDataA(fid);
        return getAStringAndFree(p);
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
        return setRealReg(screenNo, codeList, Integer.toString(fid.getFidValue()), option == RealRegistOption.KEEP ? "1" : "0");
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

    // 더 좋은 아이디어를 이슈에 남겨주시면 정말 감사하겠습니다...
    private void initEventHandler() {
        kw.kw_SetOnEventConnect((OnEventConnectCallback) errCode -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onEventConnect(errCode);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        kw.kw_SetOnReceiveTrDataA((OnReceiveTrDataCallback) (scrNo, rqName, trCode, recordName, prevNext, dataLength, errorCode, message, splmMsg) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveTrData(scrNo, rqName, trCode, recordName, prevNext, dataLength, errorCode, message, splmMsg);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        kw.kw_SetOnReceiveRealDataA((OnReceiveRealDataCallback) (realKey, realType, realData) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveRealData(realKey, realType, realData);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        kw.kw_SetOnReceiveMsgA((OnReceiveMsgCallback) (scrNo, rqName, trCode, msg) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveMsg(scrNo, rqName, trCode, msg);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        kw.kw_SetOnReceiveChejanDataA((OnReceiveChejanDataCallback) (gubun, itemCnt, fIdList) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveChejanData(gubun, itemCnt, fIdList);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        kw.kw_SetOnReceiveRealConditionA((OnReceiveRealConditionCallback) (trCode, type, conditionName, conditionIndex) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveRealCondition(trCode, type, conditionName, conditionIndex);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        kw.kw_SetOnReceiveTrConditionA((OnReceiveTrConditionCallback) (scrNo, codeList, conditionName, index, next) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveTrCondition(scrNo, codeList, conditionName, index, next);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        kw.kw_SetOnReceiveConditionVerA((OnReceiveConditionVerCallback) (ret, msg) -> {
            for (KoaEventHandler handler : eventHandlers) {
                try {
                    handler.onReceiveConditionVer(ret, msg);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
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
