package me.jungwuk.koava.models.event;

public class MsgData extends EventData {
    /**
     * 화면 번호
     */
    public final String scrNo;
    /**
     * 사용자 구분 명
     */
    public final String rqName;
    /**
     * Tran 명
     */
    public final String trCode;
    /**
     * 서버 메시지
     */
    public final String msg;

    public MsgData(String scrNo, String rqName, String trCode, String msg) {
        this.scrNo = scrNo;
        this.rqName = rqName;
        this.trCode = trCode;
        this.msg = msg;
    }
}
