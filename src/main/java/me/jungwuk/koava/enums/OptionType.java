package me.jungwuk.koava.enums;

public enum OptionType {
    CALL(2),
    PUT(3);

    final int value;

    OptionType(int v) {
        this.value = v;
    }

    public int getValue() {
        return value;
    }
}
