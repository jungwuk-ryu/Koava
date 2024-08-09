package me.jungwuk.koava.models.event;

public class RealData extends EventData {
    /**
     * 종목코드
     */
    public final String realKey;
    /**
     * 리얼타입
     */
    public final String realType;
    /**
     * 실시간 데이터 전문
     */
    public final String realData;

    public RealData(String realKey, String realType, String realData) {
        this.realKey = realKey;
        this.realType = realType;
        this.realData = realData;
    }
}
