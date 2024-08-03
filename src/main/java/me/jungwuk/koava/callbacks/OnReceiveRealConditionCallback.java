package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

/**
 * 조건검색 실시간 편입, 이탈 종목을 받을시 실행되는 콜백
 */
public interface OnReceiveRealConditionCallback extends Callback {
    /**
     * 조건검색 실시간 편입, 이탈 종목을 받을시 실행되는 콜백
     *
     * @param trCode 종목 코드
     * @param type 편입(“I”), 이탈(“D”)
     * @param conditionName 조건명
     * @param conditionIndex 조건명 인덱스
     */
    void invoke(String trCode, String type, String conditionName, String conditionIndex);
}