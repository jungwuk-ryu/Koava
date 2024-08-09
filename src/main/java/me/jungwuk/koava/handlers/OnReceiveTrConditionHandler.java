package me.jungwuk.koava.handlers;

import com.sun.jna.Callback;
import me.jungwuk.koava.models.event.TrConditionData;

/**
 * 조건검색 조회응답으로 종목리스트를 받는 핸들러
 */
public interface OnReceiveTrConditionHandler extends Callback {
    /**
     * 조건검색 조회응답으로 종목리스트를 받는 부분
     */
    void handle(TrConditionData data);
}