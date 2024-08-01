package me.jungwuk.koava.constants;

import java.util.Objects;

public class KoaCode {
    // 정상처리
    public static final int OP_ERR_NONE = 0;

    // 실패 코드
    public static final int OP_ERR_FAIL = -10;
    public static final int OP_ERR_COND_NOTFOUND = -11;
    public static final int OP_ERR_COND_MISMATCH = -12;
    public static final int OP_ERR_COND_OVERFLOW = -13;
    public static final int OP_ERR_LOGIN = -100;
    public static final int OP_ERR_CONNECT = -101;
    public static final int OP_ERR_VERSION = -102;
    public static final int OP_ERR_FIREWALL = -103;
    public static final int OP_ERR_MEMORY = -104;
    public static final int OP_ERR_INPUT = -105;
    public static final int OP_ERR_SOCKET_CLOSED = -106;
    public static final int OP_ERR_SISE_OVERFLOW = -200;
    public static final int OP_ERR_RQ_STRUCT_FAIL = -201;
    public static final int OP_ERR_RQ_STRING_FAIL = -202;
    public static final int OP_ERR_NO_DATA = -203;
    public static final int OP_ERR_OVER_MAX_DATA = -204;
    public static final int OP_ERR_DATA_RCV_FAIL = -205;
    public static final int OP_ERR_OVER_MAX_FID = -206;
    public static final int OP_ERR_REAL_CANCEL = -207;

    // 주문 관련 실패 코드
    public static final int OP_ERR_ORD_WRONG_INPUT = -300;
    public static final int OP_ERR_ORD_WRONG_ACCTNO = -301;
    public static final int OP_ERR_OTHER_ACC_USE = -302;
    public static final int OP_ERR_MIS_2BILL_EXC = -303;
    public static final int OP_ERR_MIS_5BILL_EXC = -304;
    public static final int OP_ERR_MIS_1PER_EXC = -305;
    public static final int OP_ERR_MIS_3PER_EXC = -306;
    public static final int OP_ERR_SEND_FAIL = -307;
    public static final int OP_ERR_ORD_OVERFLOW_1 = -308;
    public static final int OP_ERR_ORD_OVERFLOW_2 = -311;
    public static final int OP_ERR_MIS_300CNT_EXC = -309;
    public static final int OP_ERR_MIS_500CNT_EXC = -310;
    public static final int OP_ERR_ORD_WRONG_ACCTINFO = -340;
    public static final int OP_ERR_ORD_SYMCODE_EMPTY = -500;

    public final int code;

    public KoaCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public boolean isError() {
        return getCode() < OP_ERR_NONE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code);
    }

    @Override
    public String toString() {
        return getErrorMessage();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof KoaCode) {
            KoaCode other = (KoaCode) obj;
            return this.code == other.code;
        }

        if (obj instanceof Integer) {
            return this.code == (Integer) obj;
        }

        return super.equals(obj);
    }

    public static KoaCode fromCode(int code) {
        return new KoaCode(code);
    }

    public String getErrorMessage() {
        return getErrorMessage(getCode());
    }

    public static String getErrorMessage(int errorCode) {
        switch (errorCode) {
            case OP_ERR_NONE: return "정상처리";
            case OP_ERR_FAIL: return "실패";
            case OP_ERR_COND_NOTFOUND: return "조건번호 없음";
            case OP_ERR_COND_MISMATCH: return "조건번호와 조건식 틀림";
            case OP_ERR_COND_OVERFLOW: return "조건검색 조회요청 초과";
            case OP_ERR_LOGIN: return "사용자정보 교환실패";
            case OP_ERR_CONNECT: return "서버접속 실패";
            case OP_ERR_VERSION: return "버전처리 실패";
            case OP_ERR_FIREWALL: return "개인방화벽 실패";
            case OP_ERR_MEMORY: return "메모리보호 실패";
            case OP_ERR_INPUT: return "함수입력값 오류";
            case OP_ERR_SOCKET_CLOSED: return "통신 연결종료";
            case OP_ERR_SISE_OVERFLOW: return "시세조회 과부하";
            case OP_ERR_RQ_STRUCT_FAIL: return "전문작성 초기화 실패";
            case OP_ERR_RQ_STRING_FAIL: return "전문작성 입력값 오류";
            case OP_ERR_NO_DATA: return "데이터 없음";
            case OP_ERR_OVER_MAX_DATA: return "조회 가능한 종목수 초과";
            case OP_ERR_DATA_RCV_FAIL: return "데이터수신 실패";
            case OP_ERR_OVER_MAX_FID: return "조회 가능한 FID수초과";
            case OP_ERR_REAL_CANCEL: return "실시간 해제 오류";
            case OP_ERR_ORD_WRONG_INPUT: return "입력값 오류";
            case OP_ERR_ORD_WRONG_ACCTNO: return "계좌 비밀번호 없음";
            case OP_ERR_OTHER_ACC_USE: return "타인계좌사용 오류";
            case OP_ERR_MIS_2BILL_EXC: return "주문가격이 20억원을 초과";
            case OP_ERR_MIS_5BILL_EXC: return "주문가격이 50억원을 초과";
            case OP_ERR_MIS_1PER_EXC: return "주문수량이 총발행주식 1%초과오류";
            case OP_ERR_MIS_3PER_EXC: return "주문수량이 총발행주식 3%초과오류";
            case OP_ERR_SEND_FAIL: return "주문전송 실패";
            case OP_ERR_ORD_OVERFLOW_1: return "주문전송 과부하(1)";
            case OP_ERR_ORD_OVERFLOW_2: return "주문전송 과부하(2)";
            case OP_ERR_MIS_300CNT_EXC: return "주문수량 300계약 초과";
            case OP_ERR_MIS_500CNT_EXC: return "주문수량 500계약 초과";
            case OP_ERR_ORD_WRONG_ACCTINFO: return "계좌정보없음";
            case OP_ERR_ORD_SYMCODE_EMPTY: return "종목코드없음";
            default: return "알 수 없는 오류 코드: " + errorCode;
        }
    }


}
