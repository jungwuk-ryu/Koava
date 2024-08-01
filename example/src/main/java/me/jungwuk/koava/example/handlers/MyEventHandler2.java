package me.jungwuk.koava.example.handlers;

import me.jungwuk.koava.Koava;
import me.jungwuk.koava.callbacks.KoaEventHandler;
import me.jungwuk.koava.enums.RealTypes;

public class MyEventHandler2 extends KoaEventHandler {
    final Koava koava;

    public MyEventHandler2(Koava koava) {
        this.koava = koava;
    }

    @Override
    public void onEventConnect(int errCode) {
        System.out.println("핸들러 2에서 로그인 이벤트를 받음");
    }

    @Override
    public void onReceiveRealData(String realKey, String realType, String realData) {
        if (realType.equals("주식체결")) {
            System.out.println("종목 코드 : " + realKey);
            System.out.println("데이터 : " + realData);

            RealTypes.FID fid = RealTypes.주식체결.현재가;
            System.out.println("현재가 : " + koava.getCommRealData(realKey, fid));
            // koava.getCommRealData(realKey, 10);으로도 가져올 수 있어요
        }
    }

    @Override
    public void onReceiveMsg(String scrNo, String rqName, String trCode, String msg) {
        System.out.println("받은 메시지 : " + msg);
    }
}
