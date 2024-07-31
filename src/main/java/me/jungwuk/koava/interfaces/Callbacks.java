package me.jungwuk.koava.interfaces;

import com.sun.jna.Callback;
import com.sun.jna.WString;

@SuppressWarnings("ALL")
public abstract class Callbacks {
    public interface OnEventConnectCallback extends Callback {
        void invoke(int errCode);
    }

    public interface OnReceiveTrDataWCallback extends Callback {
        void invoke(WString sScrNo, WString sRQName, WString sTrCode,
                    WString sRecordName, WString sPrevNext, int nDataLength,
                    WString sErrorCode, WString sMessage, WString sSplmMsg);
    }

    public interface OnReceiveTrDataACallback extends Callback {
        void invoke(String sScrNo, String sRQName, String sTrCode,
                    String sRecordName, String sPrevNext, int nDataLength,
                    String sErrorCode, String sMessage, String sSplmMsg);
    }

    public interface OnReceiveRealDataWCallback extends Callback {
        void invoke(WString sRealKey, WString sRealType, WString sRealData);
    }

    public interface OnReceiveRealDataACallback extends Callback {
        void invoke(String sRealKey, String sRealType, String sRealData);
    }

    public interface OnReceiveMsgWCallback extends Callback {
        void invoke(WString sScrNo, WString sRQName, WString sTrCode, WString sMsg);
    }

    public interface OnReceiveMsgACallback extends Callback {
        void invoke(String sScrNo, String sRQName, String sTrCode, String sMsg);
    }

    public interface OnReceiveChejanDataWCallback extends Callback {
        void invoke(WString sGubun, int nItemCnt, WString sFIdList);
    }

    public interface OnReceiveChejanDataACallback extends Callback {
        void invoke(String sGubun, int nItemCnt, String sFIdList);
    }

    public interface OnReceiveRealConditionWCallback extends Callback {
        void invoke(WString sTrCode, WString strType, WString strConditionName, WString strConditionIndex);
    }

    public interface OnReceiveRealConditionACallback extends Callback {
        void invoke(String sTrCode, String strType, String strConditionName, String strConditionIndex);
    }

    public interface OnReceiveTrConditionWCallback extends Callback {
        void invoke(WString sScrNo, WString strCodeList, WString strConditionName, int nIndex, int nNext);
    }

    public interface OnReceiveTrConditionACallback extends Callback {
        void invoke(String sScrNo, String strCodeList, String strConditionName, int nIndex, int nNext);
    }

    public interface OnReceiveConditionVerWCallback extends Callback {
        void invoke(int lRet, WString sMsg);
    }

    public interface OnReceiveConditionVerACallback extends Callback {
        void invoke(int lRet, String sMsg);
    }
}
