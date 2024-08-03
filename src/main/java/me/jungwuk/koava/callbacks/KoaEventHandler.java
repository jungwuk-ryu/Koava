package me.jungwuk.koava.callbacks;

abstract public class KoaEventHandler {
    public void onEventConnect(int errCode) {

    }

    public void onReceiveChejanData(String gubun, int itemCnt, String fIdList) {

    }

    public void onReceiveConditionVer(int ret, String msg) {

    }

    public void onReceiveMsg(String scrNo, String rqName, String trCode, String msg) {

    }

    public void onReceiveRealCondition(String trCode, String type, String conditionName, String conditionIndex) {

    }

    public void onReceiveRealData(String realKey, String realType, String realData) {

    }

    public void onReceiveTrCondition(String scrNo, String codeList, String conditionName, int index, int next) {

    }

    public void onReceiveTrData(String scrNo, String rqName, String trCode,
                                String recordName, String prevNext, int dataLength,
                                String errorCode, String message, String splmMsg) {

    }
}
