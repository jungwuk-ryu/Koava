package me.jungwuk.koava.enums;

public enum HogaType {
    LIMIT("00", "지정가"),
    MARKET("03", "시장가"),
    CONDITIONAL_LIMIT("05", "조건부지정가"),
    BEST_LIMIT("06", "최유리지정가"),
    PRIORITY_LIMIT("07", "최우선지정가"),
    LIMIT_IOC("10", "지정가IOC"),
    MARKET_IOC("13", "시장가IOC"),
    BEST_IOC("16", "최유리IOC"),
    LIMIT_FOK("20", "지정가FOK"),
    MARKET_FOK("23", "시장가FOK"),
    BEST_FOK("26", "최유리FOK"),
    PRE_MARKET_CLOSE("61", "장전시간외종가"),
    AFTER_HOURS_SINGLE_PRICE("62", "시간외단일가"),
    POST_MARKET_CLOSE("81", "장후시간외종가");

    private final String code;
    private final String korean;

    HogaType(String code, String korean) {
        this.code = code;
        this.korean = korean;
    }

    public String getCode() {
        return code;
    }

    public String getKorean() {
        return korean;
    }

    public static HogaType fromCode(String code) {
        for (HogaType hogaType : HogaType.values()) {
            if (hogaType.getCode().equals(code)) {
                return hogaType;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

    public static HogaType fromKoreanCode(String korean) {
        for (HogaType hogaType : HogaType.values()) {
            if (hogaType.getKorean().equals(korean)) {
                return hogaType;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + korean);
    }

    @Override
    public String toString() {
        return name() + "(" + code + " - " + korean + ")";
    }
}
