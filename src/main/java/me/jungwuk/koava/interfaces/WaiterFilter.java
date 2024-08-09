package me.jungwuk.koava.interfaces;

public interface WaiterFilter<T> {
    boolean invoke(final T eventData);
}
