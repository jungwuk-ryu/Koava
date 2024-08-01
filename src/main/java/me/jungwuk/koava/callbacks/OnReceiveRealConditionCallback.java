package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveRealConditionCallback extends Callback {
    void invoke(String sTrCode, String strType, String strConditionName, String strConditionIndex);
}