package me.jungwuk.koava.example.handlers;

import me.jungwuk.koava.constants.KoaCode;
import me.jungwuk.koava.handlers.KoaEventHandler;
import me.jungwuk.koava.models.event.EventConnectData;

public class MyEventHandler extends KoaEventHandler {
    @Override
    public void onEventConnect(EventConnectData data) {
        System.out.println("또다른 로그인 핸들러 작동");
    }
}
