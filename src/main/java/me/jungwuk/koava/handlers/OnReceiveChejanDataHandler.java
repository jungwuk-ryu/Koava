package me.jungwuk.koava.handlers;

import me.jungwuk.koava.models.event.ChejanData;

/**
 * 체결데이터를 받으면 실행되는 핸들러
 */
public interface OnReceiveChejanDataHandler {
    /**
     * 체결데이터를 받으면 실행되는 부분
     */
    void handle(ChejanData data);
}