package me.jungwuk.koava.models;

import me.jungwuk.koava.enums.SijangType;

public class Upjong {
    /**
     * 시장 ( 0:코스피, 1: 코스닥, 2:KOSPI200, 4:KOSPI100(KOSPI50), 7:KRX100 )
     */
    final private SijangType sijangType;
    /**
     * 업종 코드
     */
    final private String code;
    /**
     * 업종명
     */
    final private String name;


    public Upjong(SijangType sijangType, String code, String name) {
        this.sijangType = sijangType;
        this.code = code;
        this.name = name;
    }

    public Upjong(String sijangGubun, String code, String name) {
        SijangType st = null;
        for (SijangType sijang : SijangType.values()) {
            if (sijang.getGubunCode().equals(sijangGubun)) {
                st = sijang;
            }
        }
        if (st == null) {
            st = SijangType.UNKNOWN;
        }

        this.sijangType = st;
        this.code = code;
        this.name = name;
    }

    /**
     * 시장 타입을 반환합니다
     * @return 시장타입(코스피,코스닥,KRX 등)
     */
    public SijangType getSijangType() {
        return sijangType;
    }

    /**
     * 업종 코드를 반환합니다
     * @return 업종 코드
     */
    public String getCode() {
        return code;
    }

    /**
     * 업종명을 반환합니다
     * @return 업종명
     */
    public String getName() {
        return name;
    }
}
