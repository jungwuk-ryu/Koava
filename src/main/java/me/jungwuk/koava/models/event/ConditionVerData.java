package me.jungwuk.koava.models.event;

public class ConditionVerData extends EventData {
    /**
     * 조건식 저장 성공여부 (1: 성공, 나머지: 실패)
     */
    public final int ret;
    /**
     * 메시지
     */
    public final String msg;

    public ConditionVerData(int ret, String msg) {
        this.ret = ret;
        this.msg = msg;
    }

    public boolean isSuccess() {
        return ret == 1;
    }
}
