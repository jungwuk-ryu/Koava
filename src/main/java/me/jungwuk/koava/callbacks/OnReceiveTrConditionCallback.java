package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveTrConditionCallback extends Callback {
    void invoke(String sScrNo, String strCodeList, String strConditionName, int nIndex, int nNext);
}