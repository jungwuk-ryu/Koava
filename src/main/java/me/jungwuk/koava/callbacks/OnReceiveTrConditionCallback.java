package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveTrConditionCallback extends Callback {
    void invoke(String scrNo, String codeList, String conditionName, int index, int next);
}