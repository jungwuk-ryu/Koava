package me.jungwuk.koava.example.handlers;

import me.jungwuk.koava.Koava;
import me.jungwuk.koava.handlers.KoaEventHandler;
import me.jungwuk.koava.enums.RealTypes;
import me.jungwuk.koava.models.event.EventConnectData;
import me.jungwuk.koava.models.event.MsgData;
import me.jungwuk.koava.models.event.RealData;

public class MyEventHandler2 extends KoaEventHandler {
    final Koava koava;

    public MyEventHandler2(Koava koava) {
        this.koava = koava;
    }

    @Override
    public void onEventConnect(EventConnectData data) {
        System.out.println("핸들러 2에서 로그인 이벤트를 받음");
    }

    @Override
    public void onReceiveRealData(RealData data) {
        if (data.realType.equals("주식체결")) {
            System.out.println("종목 코드 : " + data.realKey);
            System.out.println("데이터 : " + data.realData);

            String currentPrice = RealTypes.주식체결.현재가.get();
            /*RealTypes.FID fid = RealTypes.주식체결.현재가;
            System.out.println("현재가 : " + koava.getCommRealData(realKey, fid));
            // koava.getCommRealData(realKey, 10);으로도 가져올 수 있어요*/
        }
    }

    @Override
    public void onReceiveMsg(MsgData data) {
        System.out.println("받은 메시지 : " + data.msg);
    }
}
