package me.jungwuk.koava.enums;

public enum LoginInfoTag {
    /*
        전체 계좌 개수
     */
    ACCOUNT_CNT,

    /*
        전체 계좌 반환, ';' 으로 구분
     */
    ACCNO,

    /*
        사용자 ID 반환
     */
    USER_ID,

    /*
        사용자명 반환
     */
    USER_NAME,

    /*
        키보드 보안 해지 여부. 0: 정상, 1: 해지
     */
    KEY_BSECGB,

    /*
        방화벽 설정 여부. 0: 미설정, 1: 설정, 2: 해지
     */
    FIREW_SECGB
}
