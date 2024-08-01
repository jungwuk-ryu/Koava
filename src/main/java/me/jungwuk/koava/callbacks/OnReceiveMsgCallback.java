package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveMsgCallback extends Callback {
    void invoke(String sScrNo, String sRQName, String sTrCode, String sMsg);
}