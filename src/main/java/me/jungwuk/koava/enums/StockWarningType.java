package me.jungwuk.koava.enums;

/**
 * 주식 전종목대상 투자유의 종목 여부 타입 종류
 */
public enum StockWarningType {
    /**
     * 해당없음(0)
     */
    NONE("0", "해당없음"),

    /**
     * 정리매매(2)
     */
    REORGANIZATION_TRADING("2", "정리매매"),

    /**
     * 단기과열(3)
     */
    SHORT_TERM_OVERHEAT("3", "단기과열"),

    /**
     * 투자위험(4)
     */
    INVESTMENT_RISK("4", "투자위험"),

    /**
     * 투자경고(5)
     */
    INVESTMENT_WARNING("5", "투자경고");

    private final String code;
    private final String description;

    /**
     * @param code        경고 코드
     * @param description 경고 한글명
     */
    StockWarningType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 제공된 코드에 해당하는 StockWarningType를 반환합니다.
     *
     * @param code 경고 코드
     * @return 코드에 해당하는 StockWarningType
     * @throws IllegalArgumentException 코드가 알려지지 않은 경우
     */
    public static StockWarningType fromCode(String code) {
        for (StockWarningType status : values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }

        throw new IllegalArgumentException("알려지지 않은 StockWarning 코드: " + code);
    }

    /**
     * 코드를 반환합니다.
     *
     * @return 경고 코드
     */
    public String getCode() {
        return code;
    }

    /**
     * 경고 설명(한글명)을 반환합니다.
     *
     * @return 경고 설명
     */
    public String getDescription() {
        return description;
    }
}
