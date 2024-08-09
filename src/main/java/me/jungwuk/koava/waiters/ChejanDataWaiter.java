package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.ChejanData;

public class ChejanDataWaiter extends KoavaWaiter<ChejanData> {
    public ChejanDataWaiter() {
    }

    public ChejanDataWaiter(WaiterFilter<ChejanData> filter) {
        super(filter);
    }
}
