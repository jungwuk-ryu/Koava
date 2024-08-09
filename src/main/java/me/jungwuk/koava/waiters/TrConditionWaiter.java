package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.TrConditionData;

public class TrConditionWaiter extends KoavaWaiter<TrConditionData> {
    public TrConditionWaiter() {
        super();
    }

    public TrConditionWaiter(WaiterFilter<TrConditionData> filter) {
        super(filter);
    }
}
