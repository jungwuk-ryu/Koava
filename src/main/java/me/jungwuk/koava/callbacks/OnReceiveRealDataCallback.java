package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveRealDataCallback extends Callback {
    void invoke(String realKey, String realType, String realData);
}