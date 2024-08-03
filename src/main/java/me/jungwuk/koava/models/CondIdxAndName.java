package me.jungwuk.koava.models;

public class CondIdxAndName {
    private final String index;
    private final String name;

    public CondIdxAndName(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
