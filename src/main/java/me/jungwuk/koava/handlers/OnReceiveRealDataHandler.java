package me.jungwuk.koava.handlers;

import com.sun.jna.Callback;
import me.jungwuk.koava.models.event.RealData;

/**
 *  실시간 데이터를 받으면 호출되는 핸들러
 */
public interface OnReceiveRealDataHandler extends Callback {
    /**
     * 실시간 데이터를 받으면 호출되는 부분
     */
    void handle(RealData data);
}