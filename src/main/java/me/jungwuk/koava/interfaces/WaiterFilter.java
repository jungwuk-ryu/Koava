package me.jungwuk.koava.interfaces;

import me.jungwuk.koava.models.event.EventData;

public interface WaiterFilter<T extends EventData> {
    boolean invoke(final T eventData);
}
