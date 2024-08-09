package me.jungwuk.koava.handlers;

import me.jungwuk.koava.models.event.EventConnectData;

/**
 * 서버 접속 관련 이벤트 핸들러
 */
public interface OnEventConnectHandler {
    /**
     * 서버 접속 관련 이벤트가 발생하면 호출됩니다.<br>
     */
    void handle(EventConnectData data);
}