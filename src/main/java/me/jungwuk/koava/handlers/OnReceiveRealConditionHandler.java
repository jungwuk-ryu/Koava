package me.jungwuk.koava.handlers;

import com.sun.jna.Callback;
import me.jungwuk.koava.models.event.RealConditionData;

/**
 * 조건검색 실시간 편입, 이탈 종목을 받을시 실행되는 핸들러
 */
public interface OnReceiveRealConditionHandler extends Callback {
    /**
     * 조건검색 실시간 편입, 이탈 종목을 받을시 실행되는 부분
     */
    void handle(RealConditionData data);
}