package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.EventData;

public class KoavaWaiter<D extends EventData> {
    private D eventData;
    private WaiterFilter filter;

    public KoavaWaiter() {
        this(null);
    }

    public KoavaWaiter(final WaiterFilter filter) {
        this.filter = filter;
    }

    public synchronized void setData(final D data) {
        eventData = data;
        notify();
    }

    public synchronized D getData() {
        while (eventData == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        D data =  eventData;
        eventData = null;
        return data;
    }

    public void setFilter(final WaiterFilter filter) {
        this.filter = filter;
    }

    public boolean checkFilter(D eventData) {
        if (filter == null) return true;
        return filter.invoke(eventData);
    }
}
