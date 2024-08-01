package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveRealDataCallback extends Callback {
    void invoke(String sRealKey, String sRealType, String sRealData);
}