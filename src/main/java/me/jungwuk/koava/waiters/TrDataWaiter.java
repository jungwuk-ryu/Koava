package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.TrData;

public class TrDataWaiter extends KoavaWaiter<TrData> {
    public TrDataWaiter() {
    }

    public TrDataWaiter(WaiterFilter<TrData> filter) {
        super(filter);
    }
}
