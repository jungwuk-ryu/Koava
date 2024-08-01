package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveConditionVerCallback extends Callback {
    void invoke(int lRet, String sMsg);
}