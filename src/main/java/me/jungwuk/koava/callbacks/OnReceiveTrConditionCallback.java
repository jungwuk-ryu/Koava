package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

/**
 * 조건검색 조회응답으로 종목리스트를 구분자(“;”)로 붙여서 받는 콜백
 */
public interface OnReceiveTrConditionCallback extends Callback {
    /**
     * 조건검색 조회응답으로 종목리스트를 구분자(“;”)로 붙여서 받는 콜백
     *
     * @param scrNo 종목 코드
     * @param codeList 종목리스트(";"로 구분)
     * @param conditionName 조건명
     * @param index 조건명 인덱스
     * @param next 연속조회(2:연속조회, 0:연속조회 없음)
     */
    void invoke(String scrNo, String codeList, String conditionName, int index, int next);
}