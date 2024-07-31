package me.jungwuk.koava;

import com.sun.jna.*;
import com.sun.jna.platform.win32.COM.COMUtils;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.Variant;
import com.sun.jna.platform.win32.WinNT;
import me.jungwuk.koava.exceptions.COMInitializationException;
import me.jungwuk.koava.exceptions.UnimplementedException;
import me.jungwuk.koava.interfaces.KwLibrary;

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
    }

    void uninitialize() {
        kw.kw_Uninitialize();
    }

    void setOnEventConnect(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveTrDataW(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveTrDataA(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveRealDataW(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveRealDataA(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveMsgW(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveMsgA(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveChejanDataW(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveChejanDataA(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveRealConditionW(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveRealConditionA(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveTrConditionW(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveTrConditionA(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveConditionVerW(Callback handler) {
        throw new UnimplementedException();
    }

    void setOnReceiveConditionVerA(Callback handler) {
        throw new UnimplementedException();
    }

    void free(Pointer p) {
        throw new UnimplementedException();
    }

    void freeStringW(Pointer p) {
        throw new UnimplementedException();
    }

    void freeStringA(Pointer p) {
        throw new UnimplementedException();
    }

    void setCharsetUtf8(int useUtf8) {
        throw new UnimplementedException();
    }

    int commConnect() {
        return kw.kw_CommConnect();
    }

    int getConnectState() {
        throw new UnimplementedException();
    }

    Pointer getMasterCodeNameW(WString sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterCodeNameA(String sTrCode) {
        throw new UnimplementedException();
    }

    int getMasterListedStockCntW(WString sTrCode) {
        throw new UnimplementedException();
    }

    int getMasterListedStockCntA(String sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterConstructionW(WString sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterConstructionA(String sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterListedStockDateW(WString sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterListedStockDateA(String sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterLastPriceW(WString sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterLastPriceA(String sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterStockStateW(WString sTrCode) {
        throw new UnimplementedException();
    }

    Pointer getMasterStockStateA(String sTrCode) {
        throw new UnimplementedException();
    }

    int getDataCountW(WString strRecordName) {
        throw new UnimplementedException();
    }

    int getDataCountA(String strRecordName) {
        throw new UnimplementedException();
    }

    Pointer getOutputValueW(WString strRecordName, int nRepeatIdx, int nItemIdx) {
        throw new UnimplementedException();
    }

    Pointer getOutputValueA(String strRecordName, int nRepeatIdx, int nItemIdx) {
        throw new UnimplementedException();
    }

    Pointer getCommDataW(WString strTrCode, WString strRecordName, int nIndex, WString strItemName) {
        throw new UnimplementedException();
    }

    Pointer getCommDataA(String strTrCode, String strRecordName, int nIndex, String strItemName) {
        throw new UnimplementedException();
    }

    Pointer getCommRealDataW(WString sTrCode, int nFid) {
        throw new UnimplementedException();
    }

    Pointer getCommRealDataA(String sTrCode, int nFid) {
        throw new UnimplementedException();
    }

    Pointer getChejanDataW(int nFid) {
        throw new UnimplementedException();
    }

    Pointer getChejanDataA(int nFid) {
        throw new UnimplementedException();
    }

    Pointer getAPIModulePathW() {
        throw new UnimplementedException();
    }

    Pointer getAPIModulePathA() {
        throw new UnimplementedException();
    }

    Pointer getCodeListByMarketW(WString sMarket) {
        throw new UnimplementedException();
    }

    Pointer getCodeListByMarketA(String sMarket) {
        throw new UnimplementedException();
    }

    Pointer getFutureListW() {
        throw new UnimplementedException();
    }

    Pointer getFutureListA() {
        throw new UnimplementedException();
    }

    Pointer getActPriceListW() {
        throw new UnimplementedException();
    }

    Pointer getActPriceListA() {
        throw new UnimplementedException();
    }

    Pointer getMonthListW() {
        throw new UnimplementedException();
    }

    Pointer getMonthListA() {
        throw new UnimplementedException();
    }

    Pointer getOptionCodeW(WString strActPrice, int nCp, WString strMonth) {
        throw new UnimplementedException();
    }

    Pointer getOptionCodeA(String strActPrice, int nCp, String strMonth) {
        throw new UnimplementedException();
    }

    Pointer getOptionCodeByMonthW(WString sTrCode, int nCp, WString strMonth) {
        throw new UnimplementedException();
    }

    Pointer getOptionCodeByMonthA(String sTrCode, int nCp, String strMonth) {
        throw new UnimplementedException();
    }

    Pointer getOptionCodeByActPriceW(WString sTrCode, int nCp, int nTick) {
        throw new UnimplementedException();
    }

    Pointer getOptionCodeByActPriceA(String sTrCode, int nCp, int nTick) {
        throw new UnimplementedException();
    }

    Pointer getSFutureListW(WString strBaseAssetCode) {
        throw new UnimplementedException();
    }

    Pointer getSFutureListA(String strBaseAssetCode) {
        throw new UnimplementedException();
    }

    Pointer getSFutureCodeByIndexW(WString strBaseAssetCode, int nIndex) {
        throw new UnimplementedException();
    }

    Pointer getSFutureCodeByIndexA(String strBaseAssetCode, int nIndex) {
        throw new UnimplementedException();
    }

    Pointer getSActPriceListW(WString strBaseAssetGb) {
        throw new UnimplementedException();
    }

    Pointer getSActPriceListA(String strBaseAssetGb) {
        throw new UnimplementedException();
    }

    Pointer getSMonthListW(WString strBaseAssetGb) {
        throw new UnimplementedException();
    }

    Pointer getSMonthListA(String strBaseAssetGb) {
        throw new UnimplementedException();
    }

    Pointer getSOptionCodeW(WString strBaseAssetGb, WString strActPrice, int nCp, WString strMonth) {
        throw new UnimplementedException();
    }

    Pointer getSOptionCodeA(String strBaseAssetGb, String strActPrice, int nCp, String strMonth) {
        throw new UnimplementedException();
    }

    Pointer getSOptionCodeByMonthW(WString strBaseAssetGb, WString sTrCode, int nCp, WString strMonth) {
        throw new UnimplementedException();
    }

    Pointer getSOptionCodeByMonthA(String strBaseAssetGb, String sTrCode, int nCp, String strMonth) {
        throw new UnimplementedException();
    }

    Pointer getSOptionCodeByActPriceW(WString strBaseAssetGb, WString sTrCode, int nCp, int nTick) {
        throw new UnimplementedException();
    }

    Pointer getSOptionCodeByActPriceA(String strBaseAssetGb, String sTrCode, int nCp, int nTick) {
        throw new UnimplementedException();
    }

    Pointer getFutureCodeByIndexW(int nIndex) {
        throw new UnimplementedException();
    }

    Pointer getFutureCodeByIndexA(int nIndex) {
        throw new UnimplementedException();
    }

    Pointer getThemeGroupListW(int nType) {
        throw new UnimplementedException();
    }

    Pointer getThemeGroupListA(int nType) {
        throw new UnimplementedException();
    }

    Pointer getThemeGroupCodeW(WString strThemeCode) {
        throw new UnimplementedException();
    }

    Pointer getThemeGroupCodeA(String strThemeCode) {
        throw new UnimplementedException();
    }

    Pointer getSFOBasisAssetListW() {
        throw new UnimplementedException();
    }

    Pointer getSFOBasisAssetListA() {
        throw new UnimplementedException();
    }

    Pointer getOptionATMW() {
        throw new UnimplementedException();
    }

    Pointer getOptionATMA() {
        throw new UnimplementedException();
    }

    Pointer getSOptionATMW(WString strBaseAssetGb) {
        throw new UnimplementedException();
    }

    Pointer getSOptionATMA(String strBaseAssetGb) {
        throw new UnimplementedException();
    }

    Pointer getBranchCodeNameW() {
        throw new UnimplementedException();
    }

    Pointer getBranchCodeNameA() {
        throw new UnimplementedException();
    }

    int sendOrderCreditW(WString sRQName, WString sScreenNo, WString sAccNo, int nOrderType, WString sCode, int nQty, int nPrice, WString sHogaGb, WString sCreditGb, WString sLoanDate, WString sOrgOrderNo) {
        throw new UnimplementedException();
    }

    int sendOrderCreditA(String sRQName, String sScreenNo, String sAccNo, int nOrderType, String sCode, int nQty, int nPrice, String sHogaGb, String sCreditGb, String sLoanDate, String sOrgOrderNo) {
        throw new UnimplementedException();
    }

    Pointer KOA_FunctionsW(WString sFunctionName, WString sParam) {
        throw new UnimplementedException();
    }

    Pointer KOA_FunctionsA(String sFunctionName, String sParam) {
        throw new UnimplementedException();
    }

    int setInfoDataW(WString sInfoData) {
        throw new UnimplementedException();
    }

    int setInfoDataA(String sInfoData) {
        throw new UnimplementedException();
    }

    int setRealRegW(WString strScreenNo, WString strCodeList, WString strFidList, WString strOptType) {
        throw new UnimplementedException();
    }

    int setRealRegA(String strScreenNo, String strCodeList, String strFidList, String strOptType) {
        throw new UnimplementedException();
    }

    int getConditionLoad() {
        throw new UnimplementedException();
    }

    Pointer getConditionNameListW() {
        throw new UnimplementedException();
    }

    Pointer getConditionNameListA() {
        throw new UnimplementedException();
    }

    int sendConditionW(WString strScrNo, WString strConditionName, int nIndex, int nSearch) {
        throw new UnimplementedException();
    }

    int sendConditionA(String strScrNo, String strConditionName, int nIndex, int nSearch) {
        throw new UnimplementedException();
    }

    void sendConditionStopW(WString strScrNo, WString strConditionName, int nIndex) {
        throw new UnimplementedException();
    }

    void sendConditionStopA(String strScrNo, String strConditionName, int nIndex) {
        throw new UnimplementedException();
    }

    Variant.VARIANT getCommDataExW(WString strTrCode, WString strRecordName) {
        throw new UnimplementedException();
    }

    Variant.VARIANT getCommDataExA(String strTrCode, String strRecordName) {
        throw new UnimplementedException();
    }

    void setRealRemoveW(WString strScrNo, WString strDelCode) {
        throw new UnimplementedException();
    }

    void setRealRemoveA(String strScrNo, String strDelCode) {
        throw new UnimplementedException();
    }

    int getMarketTypeW(WString sTrCode) {
        throw new UnimplementedException();
    }

    int getMarketTypeA(String sTrCode) {
        throw new UnimplementedException();
    }

    int commRqDataW(WString sRQName, WString sTrCode, int nPrevNext, WString sScreenNo) {
        throw new UnimplementedException();
    }

    int commRqDataA(String sRQName, String sTrCode, int nPrevNext, String sScreenNo) {
        throw new UnimplementedException();
    }

    Pointer getLoginInfoW(WString sTag) {
        throw new UnimplementedException();
    }

    Pointer getLoginInfoA(String sTag) {
        throw new UnimplementedException();
    }

    int sendOrderW(WString sRQName, WString sScreenNo, WString sAccNo, int nOrderType, WString sCode, int nQty, int nPrice, WString sHogaGb, WString sOrgOrderNo) {
        throw new UnimplementedException();
    }

    int sendOrderA(String sRQName, String sScreenNo, String sAccNo, int nOrderType, String sCode, int nQty, int nPrice, String sHogaGb, String sOrgOrderNo) {
        throw new UnimplementedException();
    }

    int sendOrderFOW(WString sRQName, WString sScreenNo, WString sAccNo, WString sCode, int lOrdKind, WString sSlbyTp, WString sOrdTp, int lQty, WString sPrice, WString sOrgOrdNo) {
        throw new UnimplementedException();
    }

    int sendOrderFOA(String sRQName, String sScreenNo, String sAccNo, String sCode, int lOrdKind, String sSlbyTp, String sOrdTp, int lQty, String sPrice, String sOrgOrdNo) {
        throw new UnimplementedException();
    }

    void setInputValueW(WString sID, WString sValue) {
        throw new UnimplementedException();
    }

    void setInputValueA(String sID, String sValue) {
        throw new UnimplementedException();
    }

    void disconnectRealDataW(WString sScnNo) {
        throw new UnimplementedException();
    }

    void disconnectRealDataA(String sScnNo) {
        throw new UnimplementedException();
    }

    int getRepeatCntW(WString sTrCode, WString sRecordName) {
        throw new UnimplementedException();
    }

    int getRepeatCntA(String sTrCode, String sRecordName) {
        throw new UnimplementedException();
    }

    int commKwRqDataW(WString sArrCode, int bNext, int nCodeCount, int nTypeFlag, WString sRQName, WString sScreenNo) {
        throw new UnimplementedException();
    }

    int commKwRqDataA(String sArrCode, int bNext, int nCodeCount, int nTypeFlag, String sRQName, String sScreenNo) {
        throw new UnimplementedException();
    }

    void waitThread() {
        throw new UnimplementedException();
    }

    void sleep(int msec) {
        throw new UnimplementedException();
    }

    void disconnect() {
        throw new UnimplementedException();
    }

}
