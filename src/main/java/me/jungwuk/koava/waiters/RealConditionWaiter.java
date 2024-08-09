package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.RealConditionData;

public class RealConditionWaiter extends KoavaWaiter<RealConditionData> {
    public RealConditionWaiter() {
        super();
    }

    public RealConditionWaiter(WaiterFilter<RealConditionData> filter) {
        super(filter);
    }
}
