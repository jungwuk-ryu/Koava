package me.jungwuk.koava.models.event;

public class TrData extends EventData {
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
     * Record 명
     */
    public final String recordName;
    /**
     * 연속조회 유무
     */
    public final String prevNext;

    /**
     * 사용되지 않음
     */
    public final int dataLength;
    /**
     * 사용되지 않음
     */
    public final String errorCode;
    /**
     * 사용되지 않음
     */
    public final String message;
    /**
     * 사용되지 않음
     */
    public final String splmMsg;

    public TrData(String scrNo, String rqName, String trCode, String recordName, String prevNext, int dataLength, String errorCode, String message, String splmMsg) {
        this.scrNo = scrNo;
        this.rqName = rqName;
        this.trCode = trCode;
        this.recordName = recordName;
        this.prevNext = prevNext;
        this.dataLength = dataLength;
        this.errorCode = errorCode;
        this.message = message;
        this.splmMsg = splmMsg;
    }
}
