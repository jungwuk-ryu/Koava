package me.jungwuk.koava.waiters;

import me.jungwuk.koava.interfaces.WaiterFilter;
import me.jungwuk.koava.models.event.EventData;

import java.util.ArrayList;

public class KoavaWaiter<D extends EventData> {
    public static final ArrayList<KoavaWaiter> waiterList = new ArrayList<>();

    private final Object lock = new Object();
    private D eventData;
    private WaiterFilter<D> filter;

    public KoavaWaiter() {
        this(null);
    }

    public KoavaWaiter(final WaiterFilter<D> filter) {
        this.filter = (filter != null) ? filter : eventData -> true;
    }

    public void waitEvent() {
        waitEvent(0);
    }

    public void waitEvent(long timeoutInMs) {
        synchronized (waiterList) {
            waiterList.add(this);
        }

        try {
            synchronized (lock) {
                lock.wait(timeoutInMs);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            synchronized (waiterList) {
                waiterList.remove(this);
            }
        }
    }

    public void setData(final D data) {
        synchronized (lock) {
            eventData = data;
            lock.notifyAll();
        }
    }

    public void clearData() {
        eventData = null;
    }

    public D getData() {
        return eventData;
    }

    public void setFilter(final WaiterFilter<D> filter) {
        this.filter = filter;
    }

    /**
     * 조건에 일치하는지 확인합니다.<br>
     *
     * @param eventData 이벤트 데이터
     * @return true: filter 조건에 일치함, false: 필터 조건에 맞지 않음
     */
    public boolean checkFilter(D eventData) {
        return filter == null || filter.invoke(eventData);
    }
}
