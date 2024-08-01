package me.jungwuk.koava.example.handlers;

import me.jungwuk.koava.callbacks.KoaEventHandler;

public class MyEventHandler extends KoaEventHandler {
    @Override
    public void onEventConnect(int errCode) {
        System.out.println("또다른 로그인 핸들러 작동");
    }
}
