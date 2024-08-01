package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

public interface OnReceiveTrDataCallback extends Callback {
    void invoke(String scrNo, String rqName, String trCode,
                String recordName, String prevNext, int dataLength,
                String errorCode, String message, String splmMsg);
}