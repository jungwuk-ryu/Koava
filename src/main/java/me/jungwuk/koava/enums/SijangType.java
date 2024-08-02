package me.jungwuk.koava.enums;

public enum SijangType {
    /**
     * 코스피 (0)
     */
    KOSPI("코스피", "0"),
    /**
     * 코스닥 (1)
     */
    KOSDAQ("코스닥", "1"),
    /**
     * 코스피 200 (2)
     */
    KOSPI200("KOSPI200", "2"),
    /**
     * 코스피 100, 코스피 50 (4)
     */
    KOSPI100("KOSPI100(KOSPI50)", "4"),
    /**
     * KRX100 (7)
     */
    KRX100("KRX100", "7"),
    /**
     * 알 수 없는 시장
     */
    UNKNOWN("알 수 없는 시장", "-1");

    final String name;
    final String gubunCode;

    SijangType(String name, String sijangGubunCode) {
        this.name = name;
        this.gubunCode = sijangGubunCode;
    }

    public String getGubunCode() {
        return this.gubunCode;
    }

    public String getSijangName() {
        return this.name;
    }
}
