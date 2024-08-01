package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveTrDataCallback extends Callback {
    void invoke(String sScrNo, String sRQName, String sTrCode,
                String sRecordName, String sPrevNext, int nDataLength,
                String sErrorCode, String sMessage, String sSplmMsg);
}