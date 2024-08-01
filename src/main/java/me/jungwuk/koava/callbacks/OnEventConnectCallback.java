package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;
import me.jungwuk.koava.constants.KoaCode;

public interface OnEventConnectCallback extends Callback {
    void invoke(int errCode);
}