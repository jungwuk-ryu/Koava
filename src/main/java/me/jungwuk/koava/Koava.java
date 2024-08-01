package me.jungwuk.koava;

import com.sun.jna.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinNT;
import me.jungwuk.koava.callbacks.*;
import me.jungwuk.koava.enums.ConnectStateType;
import me.jungwuk.koava.enums.LoginInfoTag;
import me.jungwuk.koava.exceptions.COMInitializationException;
import me.jungwuk.koava.interfaces.KwLibrary;

import java.util.List;

@SuppressWarnings("unused")
public class Koava {
    private KwLibrary kw;

    public void init() throws COMInitializationException {
        WinNT.HRESULT hr = Ole32.INSTANCE.CoInitializeEx(null, Ole32.COINIT_MULTITHREADED);
        if (COMUtils.FAILED(hr)) {
            throw new COMInitializationException("CoInitializeEx를 실패하였습니다.");
        }

        kw = Native.load("kw_", KwLibrary.class);
        kw.kw_Initialize(1);
        kw.kw_SetCharsetUtf8(1);
    }

    public KwLibrary getKw() {
        return this.kw;
    }

    public void uninitialize() {
        kw.kw_Uninitialize();
    }

    public void setOnEventConnect(OnEventConnectCallback handler) {
        kw.kw_SetOnEventConnect(handler);
    }

    public void setOnReceiveTrData(OnReceiveTrDataCallback handler) {
        kw.kw_SetOnReceiveTrDataA(handler);
    }

    public void setOnReceiveRealData(OnReceiveRealDataCallback handler) {
        kw.kw_SetOnReceiveRealDataA(handler);
    }

    public void setOnReceiveMsg(OnReceiveMsgCallback handler) {
        kw.kw_SetOnReceiveMsgA(handler);
    }

    public void setOnReceiveChejanData(OnReceiveChejanDataCallback handler) {
        kw.kw_SetOnReceiveChejanDataA(handler);
    }

    public void setOnReceiveRealCondition(OnReceiveRealConditionCallback handler) {
        kw.kw_SetOnReceiveRealConditionA(handler);
    }

    public void setOnReceiveTrCondition(OnReceiveTrConditionCallback handler) {
        kw.kw_SetOnReceiveTrConditionA(handler);
    }

    public void setOnReceiveConditionVer(OnReceiveConditionVerCallback handler) {
        kw.kw_SetOnReceiveConditionVerA(handler);
    }

    public void setCharsetUtf8(boolean useUtf8) {
        kw.kw_SetCharsetUtf8(useUtf8 ? 1 : 0);
    }

    public int commConnect() {
        return kw.kw_CommConnect();
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

    public String getMasterCodeName(String sTrCode) {
        Pointer p = kw.kw_GetMasterCodeNameA(sTrCode);
        return getAStringAndFree(p);
    }

    public int getMasterListedStockCnt(String sTrCode) {
        return kw.kw_GetMasterListedStockCntA(sTrCode);
    }

    public String getMasterConstruction(String sTrCode) {
        Pointer p = kw.kw_GetMasterConstructionA(sTrCode);
        return getAStringAndFree(p);
    }

    public String getMasterListedStockDate(String sTrCode) {
        Pointer p = kw.kw_GetMasterListedStockDateA(sTrCode);
        return getAStringAndFree(p);
    }

    public String getMasterLastPrice(String sTrCode) {
        Pointer p = kw.kw_GetMasterLastPriceA(sTrCode);
        return getAStringAndFree(p);
    }

    public String getMasterStockState(String sTrCode) {
        Pointer p = kw.kw_GetMasterStockStateA(sTrCode);
        return getAStringAndFree(p);
    }

    public int getDataCount(String strRecordName) {
        return kw.kw_GetDataCountA(strRecordName);
    }

    public String getOutputValue(String strRecordName, int nRepeatIdx, int nItemIdx) {
        Pointer p = kw.kw_GetOutputValueA(strRecordName, nRepeatIdx, nItemIdx);
        return getAStringAndFree(p);
    }

    public String getCommData(String strTrCode, String strRecordName, int nIndex, String strItemName) {
        Pointer p = kw.kw_GetCommDataA(strTrCode, strRecordName, nIndex, strItemName);
        return getAStringAndFree(p);
    }

    public String getCommRealData(String sTrCode, int nFid) {
        Pointer p = kw.kw_GetCommRealDataA(sTrCode, nFid);
        return getAStringAndFree(p);
    }

    public String getChejanData(int nFid) {
        Pointer p = kw.kw_GetChejanDataA(nFid);
        return getAStringAndFree(p);
    }

    public String getAPIModulePath() {
        Pointer p = kw.kw_GetAPIModulePathA();
        return getAStringAndFree(p);
    }

    public String getCodeListByMarket(String sMarket) {
        Pointer p = kw.kw_GetCodeListByMarketA(sMarket);
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

    public String getOptionCode(String strActPrice, int nCp, String strMonth) {
        Pointer p = kw.kw_GetOptionCodeA(strActPrice, nCp, strMonth);
        return getAStringAndFree(p);
    }

    public String getOptionCodeByMonth(String sTrCode, int nCp, String strMonth) {
        Pointer p = kw.kw_GetOptionCodeByMonthA(sTrCode, nCp, strMonth);
        return getAStringAndFree(p);
    }

    public String getOptionCodeByActPrice(String sTrCode, int nCp, int nTick) {
        Pointer p = kw.kw_GetOptionCodeByActPriceA(sTrCode, nCp, nTick);
        return getAStringAndFree(p);
    }

    public String getSFutureList(String strBaseAssetCode) {
        Pointer p = kw.kw_GetSFutureListA(strBaseAssetCode);
        return getAStringAndFree(p);
    }

    public String getSFutureCodeByIndex(String strBaseAssetCode, int nIndex) {
        Pointer p = kw.kw_GetSFutureCodeByIndexA(strBaseAssetCode, nIndex);
        return getAStringAndFree(p);
    }

    public String getSActPriceList(String strBaseAssetGb) {
        Pointer p = kw.kw_GetSActPriceListA(strBaseAssetGb);
        return getAStringAndFree(p);
    }

    public String getSMonthList(String strBaseAssetGb) {
        Pointer p = kw.kw_GetSMonthListA(strBaseAssetGb);
        return getAStringAndFree(p);
    }

    public String getSOptionCode(String strBaseAssetGb, String strActPrice, int nCp, String strMonth) {
        Pointer p = kw.kw_GetSOptionCodeA(strBaseAssetGb, strActPrice, nCp, strMonth);
        return getAStringAndFree(p);
    }

    public String getSOptionCodeByMonth(String strBaseAssetGb, String sTrCode, int nCp, String strMonth) {
        Pointer p = kw.kw_GetSOptionCodeByMonthA(strBaseAssetGb, sTrCode, nCp, strMonth);
        return getAStringAndFree(p);
    }

    public String getSOptionCodeByActPrice(String strBaseAssetGb, String sTrCode, int nCp, int nTick) {
        Pointer p = kw.kw_GetSOptionCodeByActPriceA(strBaseAssetGb, sTrCode, nCp, nTick);
        return getAStringAndFree(p);
    }

    public String getFutureCodeByIndex(int nIndex) {
        Pointer p = kw.kw_GetFutureCodeByIndexA(nIndex);
        return getAStringAndFree(p);
    }

    public String getThemeGroupList(int nType) {
        Pointer p = kw.kw_GetThemeGroupListA(nType);
        return getAStringAndFree(p);
    }

    public String getThemeGroupCode(String strThemeCode) {
        Pointer p = kw.kw_GetThemeGroupCodeA(strThemeCode);
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

    public String getSOptionATM(String strBaseAssetGb) {
        Pointer p = kw.kw_GetSOptionATMA(strBaseAssetGb);
        return getAStringAndFree(p);
    }

    public String getBranchCodeName() {
        Pointer pointer = kw.kw_GetBranchCodeNameA();
        return getAStringAndFree(pointer);
    }

    public int sendOrderCredit(String sRQName, String sScreenNo, String sAccNo, int nOrderType, String sCode, int nQty, int nPrice, String sHogaGb, String sCreditGb, String sLoanDate, String sOrgOrderNo) {
        return kw.kw_SendOrderCreditA(sRQName, sScreenNo, sAccNo, nOrderType, sCode, nQty, nPrice, sHogaGb, sCreditGb, sLoanDate, sOrgOrderNo);
    }

    public String koaFunctions(String sFunctionName, String sParam) {
        Pointer p = kw.kw_KOA_FunctionsA(sFunctionName, sParam);
        return getAStringAndFree(p);
    }

    public int setInfoData(String sInfoData) {
        return kw.kw_SetInfoDataA(sInfoData);
    }

    public int setRealReg(String strScreenNo, String strCodeList, String strFidList, String strOptType) {
        return kw.kw_SetRealRegA(strScreenNo, strCodeList, strFidList, strOptType);
    }

    public int getConditionLoad() {
        return kw.kw_GetConditionLoad();
    }

    public String getConditionNameList() {
        Pointer p = kw.kw_GetConditionNameListA();
        return getAStringAndFree(p);
    }

    public int sendCondition(String strScrNo, String strConditionName, int nIndex, int nSearch) {
        return kw.kw_SendConditionA(strScrNo, strConditionName, nIndex, nSearch);
    }

    public void sendConditionStop(String strScrNo, String strConditionName, int nIndex) {
        kw.kw_SendConditionStopA(strScrNo, strConditionName, nIndex);
    }

    public Variant.VARIANT getCommDataEx(String strTrCode, String strRecordName) {
        return kw.kw_GetCommDataExA(strTrCode, strRecordName);
    }

    public void setRealRemove(String strScrNo, String strDelCode) {
        kw.kw_SetRealRemoveA(strScrNo, strDelCode);
    }

    public int getMarketType(String sTrCode) {
        return kw.kw_GetMarketTypeA(sTrCode);
    }

    public int commRqData(String sRQName, String sTrCode, int nPrevNext, String sScreenNo) {
        return kw.kw_CommRqDataA(sRQName, sTrCode, nPrevNext, sScreenNo);
    }

    public String getLoginInfo(LoginInfoTag tag) {
        Pointer pointer = kw.kw_GetLoginInfoA(tag.name());
        return getAStringAndFree(pointer);
    }

    public int sendOrder(String sRQName, String sScreenNo, String sAccNo, int nOrderType, String sCode, int nQty, int nPrice, String sHogaGb, String sOrgOrderNo) {
        return kw.kw_SendOrderA(sRQName, sScreenNo, sAccNo, nOrderType, sCode, nQty, nPrice, sHogaGb, sOrgOrderNo);
    }

    public int sendOrderFO(String sRQName, String sScreenNo, String sAccNo, String sCode, int lOrdKind, String sSlbyTp, String sOrdTp, int lQty, String sPrice, String sOrgOrdNo) {
        return kw.kw_SendOrderFOA(sRQName, sScreenNo, sAccNo, sCode, lOrdKind, sSlbyTp, sOrdTp, lQty, sPrice, sOrgOrdNo);
    }

    public void setInputValue(String sID, String sValue) {
        kw.kw_SetInputValueA(sID, sValue);
    }

    public void disconnectRealData(String sScnNo) {
        kw.kw_DisconnectRealDataA(sScnNo);
    }

    public int getRepeatCnt(String sTrCode, String sRecordName) {
        return kw.kw_GetRepeatCntA(sTrCode, sRecordName);
    }

    public int commKwRqData(String sArrCode, boolean bNext, int nCodeCount, int nTypeFlag, String sRQName, String sScreenNo) {
        return kw.kw_CommKwRqDataA(sArrCode, bNext ? 1 : 0, nCodeCount, nTypeFlag, sRQName, sScreenNo);
    }

    public int commKwRqData(List<String> codeList, boolean bNext, int nCodeCount, int nTypeFlag, String sRQName, String sScreenNo) {
        StringBuilder sb = new StringBuilder();
        int listSize = codeList.size();

        for (int i = 0; i < listSize; i++) {
            if (i != 0) sb.append(";");
            sb.append(codeList.get(i));
        }

        return commKwRqData(sb.toString(), bNext, nCodeCount, nTypeFlag, sRQName, sScreenNo);
    }

    public int commKwRqData(String[] codeArr, boolean bNext, int nCodeCount, int nTypeFlag, String sRQName, String sScreenNo) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < codeArr.length; i++) {
            if (i != 0) sb.append(";");
            sb.append(codeArr[i]);
        }

        return commKwRqData(sb.toString(), bNext, nCodeCount, nTypeFlag, sRQName, sScreenNo);
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

}
