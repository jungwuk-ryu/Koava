package me.jungwuk.koava.enums;

public enum OrderType {
    NEW_BUY(1, "신규매수"),
    NEW_SELL(2, "신규매도"),
    CANCEL_BUY(3, "매수취소"),
    CANCEL_SELL(4, "매도취소"),
    MODIFY_BUY(5, "매수정정"),
    MODIFY_SELL(6, "매도정정");

    private final int code;
    private final String name;

    OrderType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static OrderType fromCode(int code) {
        for (OrderType type : OrderType.values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public static OrderType fromName(String name) {
        for (OrderType type : OrderType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException("Invalid name: " + name);
    }

    @Override
    public String toString() {
        return code + ": " + name;
    }
}
