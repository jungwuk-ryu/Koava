package me.jungwuk.koava.models.event;

import me.jungwuk.koava.constants.KoaCode;

public class EventConnectData extends EventData {
    public final KoaCode errCode;

    public EventConnectData(int errCode) {
        this.errCode = KoaCode.fromCode(errCode);
    }
}
