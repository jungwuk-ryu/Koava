package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.EventConnectData;

public class EventConnectWaiter extends KoavaWaiter<EventConnectData> {
    public EventConnectWaiter() {
        super();
    }

    public EventConnectWaiter(WaiterFilter<EventConnectData> filter) {
        super(filter);
    }
}
