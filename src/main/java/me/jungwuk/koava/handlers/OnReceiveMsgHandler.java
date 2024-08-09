package me.jungwuk.koava.handlers;


import me.jungwuk.koava.models.event.MsgData;

/**
 * 서버통신 후 메시지를 받으면 실행되는 핸들러
 */
public interface OnReceiveMsgHandler {
    /**
     * 서버통신 후 메시지를 받으면 실행되는 부분
     */
    void handle(MsgData data);
}