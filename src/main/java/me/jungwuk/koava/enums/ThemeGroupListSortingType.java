package me.jungwuk.koava.enums;

public enum ThemeGroupListSortingType {
    /**
     * 코드 순으로 정렬
     */
    CODE(0),

    /**
     * 테마 순으로 정렬
     */
    THEME(1);

    public final int typeCode;

    ThemeGroupListSortingType(int code) {
        this.typeCode = code;
    }

    public int getTypeCode() {
        return typeCode;
    }
}
