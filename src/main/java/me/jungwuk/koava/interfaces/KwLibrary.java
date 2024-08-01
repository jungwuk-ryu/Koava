package me.jungwuk.koava.interfaces;


import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.Variant;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public interface KwLibrary extends Library {

    int kw_Initialize(int option);

    void kw_Uninitialize();

    void kw_SetOnEventConnect(Callback handler);

    void kw_SetOnReceiveTrDataW(Callback handler);

    void kw_SetOnReceiveTrDataA(Callback handler);

    void kw_SetOnReceiveRealDataW(Callback handler);

    void kw_SetOnReceiveRealDataA(Callback handler);

    void kw_SetOnReceiveMsgW(Callback handler);

    void kw_SetOnReceiveMsgA(Callback handler);

    void kw_SetOnReceiveChejanDataW(Callback handler);

    void kw_SetOnReceiveChejanDataA(Callback handler);

    void kw_SetOnReceiveRealConditionW(Callback handler);

    void kw_SetOnReceiveRealConditionA(Callback handler);

    void kw_SetOnReceiveTrConditionW(Callback handler);

    void kw_SetOnReceiveTrConditionA(Callback handler);

    void kw_SetOnReceiveConditionVerW(Callback handler);

    void kw_SetOnReceiveConditionVerA(Callback handler);

    void kw_Free(Pointer p);

    void kw_FreeStringW(Pointer p);

    void kw_FreeStringA(Pointer p);

    void kw_SetCharsetUtf8(int useUtf8);

    int kw_CommConnect();

    int kw_GetConnectState();

    Pointer kw_GetMasterCodeNameW(WString sTrCode);

    Pointer kw_GetMasterCodeNameA(String sTrCode);

    int kw_GetMasterListedStockCntW(WString sTrCode);

    int kw_GetMasterListedStockCntA(String sTrCode);

    Pointer kw_GetMasterConstructionW(WString sTrCode);

    Pointer kw_GetMasterConstructionA(String sTrCode);

    Pointer kw_GetMasterListedStockDateW(WString sTrCode);

    Pointer kw_GetMasterListedStockDateA(String sTrCode);

    Pointer kw_GetMasterLastPriceW(WString sTrCode);

    Pointer kw_GetMasterLastPriceA(String sTrCode);

    Pointer kw_GetMasterStockStateW(WString sTrCode);

    Pointer kw_GetMasterStockStateA(String sTrCode);

    int kw_GetDataCountW(WString strRecordName);

    int kw_GetDataCountA(String strRecordName);

    Pointer kw_GetOutputValueW(WString strRecordName, int nRepeatIdx, int nItemIdx);

    Pointer kw_GetOutputValueA(String strRecordName, int nRepeatIdx, int nItemIdx);

    Pointer kw_GetCommDataW(WString strTrCode, WString strRecordName, int nIndex, WString strItemName);

    Pointer kw_GetCommDataA(String strTrCode, String strRecordName, int nIndex, String strItemName);

    Pointer kw_GetCommRealDataW(WString sTrCode, int nFid);

    Pointer kw_GetCommRealDataA(String sTrCode, int nFid);

    Pointer kw_GetChejanDataW(int nFid);

    Pointer kw_GetChejanDataA(int nFid);

    Pointer kw_GetAPIModulePathW();

    Pointer kw_GetAPIModulePathA();

    Pointer kw_GetCodeListByMarketW(WString sMarket);

    Pointer kw_GetCodeListByMarketA(String sMarket);

    Pointer kw_GetFutureListW();

    Pointer kw_GetFutureListA();

    Pointer kw_GetActPriceListW();

    Pointer kw_GetActPriceListA();

    Pointer kw_GetMonthListW();

    Pointer kw_GetMonthListA();

    Pointer kw_GetOptionCodeW(WString strActPrice, int nCp, WString strMonth);

    Pointer kw_GetOptionCodeA(String strActPrice, int nCp, String strMonth);

    Pointer kw_GetOptionCodeByMonthW(WString sTrCode, int nCp, WString strMonth);

    Pointer kw_GetOptionCodeByMonthA(String sTrCode, int nCp, String strMonth);

    Pointer kw_GetOptionCodeByActPriceW(WString sTrCode, int nCp, int nTick);

    Pointer kw_GetOptionCodeByActPriceA(String sTrCode, int nCp, int nTick);

    Pointer kw_GetSFutureListW(WString strBaseAssetCode);

    Pointer kw_GetSFutureListA(String strBaseAssetCode);

    Pointer kw_GetSFutureCodeByIndexW(WString strBaseAssetCode, int nIndex);

    Pointer kw_GetSFutureCodeByIndexA(String strBaseAssetCode, int nIndex);

    Pointer kw_GetSActPriceListW(WString strBaseAssetGb);

    Pointer kw_GetSActPriceListA(String strBaseAssetGb);

    Pointer kw_GetSMonthListW(WString strBaseAssetGb);

    Pointer kw_GetSMonthListA(String strBaseAssetGb);

    Pointer kw_GetSOptionCodeW(WString strBaseAssetGb, WString strActPrice, int nCp, WString strMonth);

    Pointer kw_GetSOptionCodeA(String strBaseAssetGb, String strActPrice, int nCp, String strMonth);

    Pointer kw_GetSOptionCodeByMonthW(WString strBaseAssetGb, WString sTrCode, int nCp, WString strMonth);

    Pointer kw_GetSOptionCodeByMonthA(String strBaseAssetGb, String sTrCode, int nCp, String strMonth);

    Pointer kw_GetSOptionCodeByActPriceW(WString strBaseAssetGb, WString sTrCode, int nCp, int nTick);

    Pointer kw_GetSOptionCodeByActPriceA(String strBaseAssetGb, String sTrCode, int nCp, int nTick);

    Pointer kw_GetFutureCodeByIndexW(int nIndex);

    Pointer kw_GetFutureCodeByIndexA(int nIndex);

    Pointer kw_GetThemeGroupListW(int nType);

    Pointer kw_GetThemeGroupListA(int nType);

    Pointer kw_GetThemeGroupCodeW(WString strThemeCode);

    Pointer kw_GetThemeGroupCodeA(String strThemeCode);

    Pointer kw_GetSFOBasisAssetListW();

    Pointer kw_GetSFOBasisAssetListA();

    Pointer kw_GetOptionATMW();

    Pointer kw_GetOptionATMA();

    Pointer kw_GetSOptionATMW(WString strBaseAssetGb);

    Pointer kw_GetSOptionATMA(String strBaseAssetGb);

    Pointer kw_GetBranchCodeNameW();

    Pointer kw_GetBranchCodeNameA();

    int kw_SendOrderCreditW(WString sRQName, WString sScreenNo, WString sAccNo, int nOrderType, WString sCode, int nQty, int nPrice, WString sHogaGb, WString sCreditGb, WString sLoanDate, WString sOrgOrderNo);

    int kw_SendOrderCreditA(String sRQName, String sScreenNo, String sAccNo, int nOrderType, String sCode, int nQty, int nPrice, String sHogaGb, String sCreditGb, String sLoanDate, String sOrgOrderNo);

    Pointer kw_KOA_FunctionsW(WString sFunctionName, WString sParam);

    Pointer kw_KOA_FunctionsA(String sFunctionName, String sParam);

    int kw_SetInfoDataW(WString sInfoData);

    int kw_SetInfoDataA(String sInfoData);

    int kw_SetRealRegW(WString strScreenNo, WString strCodeList, WString strFidList, WString strOptType);

    int kw_SetRealRegA(String strScreenNo, String strCodeList, String strFidList, String strOptType);

    int kw_GetConditionLoad();

    Pointer kw_GetConditionNameListW();

    Pointer kw_GetConditionNameListA();

    int kw_SendConditionW(WString strScrNo, WString strConditionName, int nIndex, int nSearch);

    int kw_SendConditionA(String strScrNo, String strConditionName, int nIndex, int nSearch);

    void kw_SendConditionStopW(WString strScrNo, WString strConditionName, int nIndex);

    void kw_SendConditionStopA(String strScrNo, String strConditionName, int nIndex);

    Variant.VARIANT kw_GetCommDataExW(WString strTrCode, WString strRecordName);

    Variant.VARIANT kw_GetCommDataExA(String strTrCode, String strRecordName);

    void kw_SetRealRemoveW(WString strScrNo, WString strDelCode);

    void kw_SetRealRemoveA(String strScrNo, String strDelCode);

    int kw_GetMarketTypeW(WString sTrCode);

    int kw_GetMarketTypeA(String sTrCode);

    int kw_CommRqDataW(WString sRQName, WString sTrCode, int nPrevNext, WString sScreenNo);

    int kw_CommRqDataA(String sRQName, String sTrCode, int nPrevNext, String sScreenNo);

    Pointer kw_GetLoginInfoW(WString sTag);

    Pointer kw_GetLoginInfoA(String sTag);

    int kw_SendOrderW(WString sRQName, WString sScreenNo, WString sAccNo, int nOrderType, WString sCode, int nQty, int nPrice, WString sHogaGb, WString sOrgOrderNo);

    int kw_SendOrderA(String sRQName, String sScreenNo, String sAccNo, int nOrderType, String sCode, int nQty, int nPrice, String sHogaGb, String sOrgOrderNo);

    int kw_SendOrderFOW(WString sRQName, WString sScreenNo, WString sAccNo, WString sCode, int lOrdKind, WString sSlbyTp, WString sOrdTp, int lQty, WString sPrice, WString sOrgOrdNo);

    int kw_SendOrderFOA(String sRQName, String sScreenNo, String sAccNo, String sCode, int lOrdKind, String sSlbyTp, String sOrdTp, int lQty, String sPrice, String sOrgOrdNo);

    void kw_SetInputValueW(WString sID, WString sValue);

    void kw_SetInputValueA(String sID, String sValue);

    void kw_DisconnectRealDataW(WString sScnNo);

    void kw_DisconnectRealDataA(String sScnNo);

    int kw_GetRepeatCntW(WString sTrCode, WString sRecordName);

    int kw_GetRepeatCntA(String sTrCode, String sRecordName);

    int kw_CommKwRqDataW(WString sArrCode, int bNext, int nCodeCount, int nTypeFlag, WString sRQName, WString sScreenNo);

    int kw_CommKwRqDataA(String sArrCode, int bNext, int nCodeCount, int nTypeFlag, String sRQName, String sScreenNo);

    void kw_Wait();

    void kw_Sleep(int msec);

    void kw_Disconnect();
}