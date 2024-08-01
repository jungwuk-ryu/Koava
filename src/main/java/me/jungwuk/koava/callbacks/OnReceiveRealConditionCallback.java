package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveRealConditionCallback extends Callback {
    void invoke(String trCode, String type, String conditionName, String conditionIndex);
}