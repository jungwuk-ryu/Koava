package me.jungwuk.koava.interfaces.callbacks;

import com.sun.jna.Callback;

/**
 * 서버 접속 관련 이벤트 콜백
 */
public interface OnEventConnectCallback extends Callback {
    /**
     * 서버 접속 관련 이벤트가 발생하면 호출됩니다.<br>
     *
     * @param errCode 에러 코드. nErrCode가 0이면 로그인 성공, 음수면 실패<br><b>{@link me.jungwuk.koava.constants.KoaCode#fromCode(int)} 를 통해 쉽게 핸들릴 할 수 있습니다.}</b>
     */
    void invoke(int errCode);
}