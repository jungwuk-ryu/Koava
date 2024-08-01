package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveChejanDataCallback extends Callback {
    void invoke(String sGubun, int nItemCnt, String sFIdList);
}