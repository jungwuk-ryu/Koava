package me.jungwuk.koava.interfaces.callbacks;

import com.sun.jna.Callback;

/**
 * 체결데이터를 받으면 실행되는 콜백
 */
public interface OnReceiveChejanDataCallback extends Callback {
    /**
     * 체결데이터를 받으면 실행되는 콜백
     *
     * @param gubun 체결구분 ({@code 0}: 주문체결통보, {@code 1}: 국내주식 잔고통보, {@code 4}: 파생상품 잔고통보)
     * @param itemCnt 아이템 개수
     * @param fIdList 데이터 리스트 (";"으로 구분)
     */
    void invoke(String gubun, int itemCnt, String fIdList);
}