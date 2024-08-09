package me.jungwuk.koava.models.event;

public class RealConditionData extends EventData {
    /**
     * 종목 코드
     */
    public final String trCode;
    /**
     * 편입(“I”), 이탈(“D”)
     */
    public final String type;
    /**
     * 조건명
     */
    public final String conditionName;
    /**
     * 조건명 인덱스
     */
    public final String conditionIndex;

    public RealConditionData(String trCode, String type, String conditionName, String conditionIndex) {
        this.trCode = trCode;
        this.type = type;
        this.conditionName = conditionName;
        this.conditionIndex = conditionIndex;
    }
}
