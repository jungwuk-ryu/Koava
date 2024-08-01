package me.jungwuk.koava.example;

import me.jungwuk.koava.*;
import me.jungwuk.koava.constants.KoaCode;
import me.jungwuk.koava.enums.LoginInfoTag;
import me.jungwuk.koava.enums.RealRegistOption;
import me.jungwuk.koava.enums.RealTypes;
import me.jungwuk.koava.example.handlers.MyEventHandler;
import me.jungwuk.koava.example.handlers.MyEventHandler2;

public class Main {
    public static void main(String[] args) {
        Koava k = Koava.getInstance();
        // 초기화
        k.init();

        // 로그인 이벤트 핸들러
        k.setOnEventConnect(errCode -> {
            KoaCode code = KoaCode.fromCode(errCode);

            if (!code.isError()) {
                System.out.println("로그인 성공");

                String name = k.getLoginInfo(LoginInfoTag.USER_NAME);
                System.out.println("접속 유저 이름 : " + name);

                k.setRealReg("1000", "005930;000660", new RealTypes.FID[] {RealTypes.FID.현재가}, RealRegistOption.CLEAR);
                // 위 코드는 아래 코드들과 동일합니다.
                /*
                 k.setRealReg("1000", "005930;000660", new RealTypes.FID[] {RealTypes.getFidByNum(10)}, RealRegistOption.CLEAR);

                k.setRealReg("1000", "005930;000660", RealTypes.FID.현재가, RealRegistOption.CLEAR);
                k.setRealReg("1000", "005930;000660", RealTypes.getFidByNum(10), RealRegistOption.CLEAR);

                k.setRealReg("1000", "005930;000660", "10", RealRegistOption.CLEAR);

                // 여러 fid를 String으로 넣는 방법
                k.setRealReg("1000", "005930;000660", "10;20", RealRegistOption.CLEAR);
                 */
            } else {
                System.out.println("오류 발생 : " + code.getErrorMessage());
                System.out.println(errCode);
                k.disconnect();
            }
        });

        // 여러개의 이벤트 핸들러 등록 가능
        k.addEventHandler(new MyEventHandler());
        k.addEventHandler(new MyEventHandler2(k));

        // 로그인 창 실행
        KoaCode code = k.commConnect();
        if (code.isError()) {
            System.out.println("로그인 창을 열지 못했습니다! " + code.getErrorMessage());
            Runtime.getRuntime().exit(1);
        }

        k.waitDisconnection();
    }
}