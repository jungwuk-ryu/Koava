package me.jungwuk.koava.interfaces.callbacks;

import com.sun.jna.Callback;

/**
 *  실시간 데이터를 받으면 호출되는 콜백
 */
public interface OnReceiveRealDataCallback extends Callback {
    /**
     * 실시간 데이터를 받으면 호출되는 콜백
     *
     * @param realKey 종목코드
     * @param realType 리얼타입
     * @param realData 실시간 데이터 전문
     */
    void invoke(String realKey, String realType, String realData);
}