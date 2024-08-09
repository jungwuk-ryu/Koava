package me.jungwuk.koava.handlers;

import com.sun.jna.Callback;
import me.jungwuk.koava.models.event.TrData;

/**
 *  서버통신 후 데이터를 받은 시점 핸들러
 */
public interface OnReceiveTrDataHandler extends Callback {
    /**
     * 서버통신 후 데이터를 받은 시점에 실행되는 부분
     */
    void handle(TrData data);
}