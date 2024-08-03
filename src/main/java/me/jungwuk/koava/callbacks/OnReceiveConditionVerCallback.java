package me.jungwuk.koava.callbacks;

import com.sun.jna.Callback;

/**
 * 로컬에 사용자 조건식 저장 성공 여부를 확인하는 콜백
 */
public interface OnReceiveConditionVerCallback extends Callback {
    /**
     * 로컬에 사용자 조건식 저장 성공 여부를 확인하는 콜백
     *
     * @param ret 조건식 저장 성공여부 (1: 성공, 나머지: 실패)
     * @param msg 메시지
     */
    void invoke(int ret, String msg);
}