package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.ConditionVerData;

public class ConditionVerWaiter extends KoavaWaiter<ConditionVerData> {
    public ConditionVerWaiter() {
    }

    public ConditionVerWaiter(WaiterFilter<ConditionVerData> filter) {
        super(filter);
    }
}
