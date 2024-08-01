package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveMsgCallback extends Callback {
    void invoke(String scrNo, String rqName, String trCode, String msg);
}