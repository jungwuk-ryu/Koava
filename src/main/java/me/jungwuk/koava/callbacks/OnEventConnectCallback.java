package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnEventConnectCallback extends Callback {
    void invoke(int errCode);
}