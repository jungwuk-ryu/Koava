package me.jungwuk.koava.handlers;

import me.jungwuk.koava.models.event.ConditionVerData;

/**
 * 로컬에 사용자 조건식 저장 성공 여부를 확인하는 핸들러
 */
public interface OnReceiveConditionVerHandler {
    /**
     * 로컬에 사용자 조건식 저장 성공 여부를 확인하는 핸들링하는 부분
     */
    void handle(ConditionVerData data);
}