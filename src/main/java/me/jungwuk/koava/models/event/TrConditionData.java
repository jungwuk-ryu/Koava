package me.jungwuk.koava.models.event;

import me.jungwuk.koava.utils.KoavaUtils;

import java.util.List;

public class TrConditionData extends EventData {
    /**
     * 화면 번호
     */
    public final String scrNo;
    /**
     * 종목 코드 리스트
     */
    public final List<String> codeList;
    /**
     * 조건명
     */
    public final String conditionName;
    /**
     * 조건명 인덱스
     */
    public final int index;
    /**
     * 연속조회(2:연속조회, 0:연속조회 없음)
     */
    public final int next;

    public TrConditionData(String scrNo, String codeList, String conditionName, int index, int next) {

        this.scrNo = scrNo;
        this.codeList = KoavaUtils.strListToList(codeList);
        this.conditionName = conditionName;
        this.index = index;
        this.next = next;
    }

    public boolean isContinuous() {
        return next == 2;
    }
}
