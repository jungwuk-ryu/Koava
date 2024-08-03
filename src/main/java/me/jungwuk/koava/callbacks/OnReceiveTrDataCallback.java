package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

/**
 *  서버통신 후 데이터를 받은 시점을 알 수 있는 콜백
 */
public interface OnReceiveTrDataCallback extends Callback {
    /**
     * 콜백 호출
     *
     * @param scrNo 화면 번호
     * @param rqName 사용자 구분 명
     * @param trCode Tran 명
     * @param recordName Record 명
     * @param prevNext 연속조회 유무
     * @param dataLength 사용되지 않음
     * @param errorCode 사용되지 않음
     * @param message 사용되지 않음
     * @param splmMsg 사용되지 않음
     */
    void invoke(String scrNo, String rqName, String trCode,
                String recordName, String prevNext, int dataLength,
                String errorCode, String message, String splmMsg);
}