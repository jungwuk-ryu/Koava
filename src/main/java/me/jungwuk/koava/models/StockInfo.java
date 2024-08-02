package me.jungwuk.koava.models;

/**
 *
 * getMasterStockInfo 메소드에서 사용하는 모델입니다.<br>
 * 문자열 {@code 시장구분0|거래소;시장구분1|중형주;업종구분|금융업;}를 모델화 했습니다.
 */
public class StockInfo {
    /**
     * 시장 구분 0 (ex: 거래소)
     */
    public final String sijangGubun0;
    /**
     * 시장 구분 1 (ex: 중형주)
     */
    public final String sijangGubun1;
    /**
     * 업종 구분 (ex: 금융업)
     */
    public final String upjongGubun;

    public StockInfo(String sijangGubun0, String sijangGubun1, String upjongGubun) {
        this.sijangGubun0 = sijangGubun0;
        this.sijangGubun1 = sijangGubun1;
        this.upjongGubun = upjongGubun;
    }
}
