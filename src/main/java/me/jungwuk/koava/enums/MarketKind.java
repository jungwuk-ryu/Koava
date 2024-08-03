package me.jungwuk.koava.enums;

/**
 * 시장 종류를 나타내는 enum입니다.<br>
 * 시장 종류 : {@code KOSPI}, {@code KOSDAQ}, {@code ELW}, {@code ETF}, {@code MUTUAL_FUND}, {@code REIT}, {@code HIGH_YIELD_FUND}, {@code THIRD_MARKET}, {@code ETN}
 *
 * @see me.jungwuk.koava.Koava#getStockMarketKind(String)
 */
public enum MarketKind {
    /**
     * 코스피
     */
    KOSPI("코스피", "0"),
    /**
     * 코스닥
     */
    KOSDAQ("코스닥", "10"),
    /**
     * ELW
     */
    ELW("ELW", "3"),
    /**
     * ETF
     */
    ETF("ETF", "8"),
    /**
     * 뮤추얼펀드 (Mutual Fund)
     */
    MUTUAL_FUND("뮤추얼펀드", "4", "14"),
    /**
     * 리츠 (Real Estate Investment Trust)
     */
    REIT("리츠", "6", "16"),
    /**
     * 하이일드펀드 (High Yield Fund)
     */
    HIGH_YIELD_FUND("하이일드펀드", "9", "19"),
    /**
     * 제3시장
     */
    THIRD_MARKET("제3시장", "30"),
    /**
     * ETN
     */
    ETN("ETN", "60");

    private final String[] codes;
    private final String koreanName;

    MarketKind(String koreanName, String... codes) {
        this.koreanName = koreanName;
        this.codes = codes;
    }

    public static MarketKind fromCode(String code) throws IllegalArgumentException {
        for (MarketKind kind : MarketKind.values()) {
            for (String kindCode : kind.codes) {
                if (kindCode.equals(code)) {
                    return kind;
                }
            }
        }
        throw new IllegalArgumentException("알 수 없는 코드 : " + code);
    }

    public String[] getCodes() {
        return codes;
    }

    public String getKoreanName() {
        return koreanName;
    }
}

