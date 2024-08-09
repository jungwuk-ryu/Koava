package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.MsgData;

public class MsgWaiter extends KoavaWaiter<MsgData> {
    public MsgWaiter() {
    }

    public MsgWaiter(WaiterFilter<MsgData> filter) {
        super(filter);
    }
}
