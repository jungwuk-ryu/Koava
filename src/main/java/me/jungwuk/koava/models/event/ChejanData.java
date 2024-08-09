package me.jungwuk.koava.models.event;

import me.jungwuk.koava.utils.KoavaUtils;

import java.util.List;

public class ChejanData extends EventData {
    /**
     * 체결구분 ({@code 0}: 주문체결통보, {@code 1}: 국내주식 잔고통보, {@code 4}: 파생상품 잔고통보)
     */
    public final String gubun;

    /**
     * 아이템 개수
     */
    public final int itemCnt;

    /**
     * 데이터 리스트
     */
    public final List<String> fIdList;

    public ChejanData(String gubun, int itemCnt, String fIdList) {
        this.gubun = gubun;
        this.itemCnt = itemCnt;
        this.fIdList = KoavaUtils.strListToList(fIdList);
    }
}
