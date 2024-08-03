package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

/**
 * 서버통신 후 메시지를 받으면 실행되는 콜백
 */
public interface OnReceiveMsgCallback extends Callback {
    /**
     * 서버통신 후 메시지를 받으면 실행되는 콜백
     *
     * @param scrNo 화면 번호
     * @param rqName 사용자 구분 명
     * @param trCode Tran 명
     * @param msg 서버 메시지
     */
    void invoke(String scrNo, String rqName, String trCode, String msg);
}