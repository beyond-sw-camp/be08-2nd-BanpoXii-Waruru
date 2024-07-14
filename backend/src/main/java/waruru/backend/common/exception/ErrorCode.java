package waruru.backend.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    NOT_FOUND_SALE(404, "SA_001", "존재하지 않는 매물입니다."),
    DUPLICATED_SALE_NAME(400, "SA_002", "이미 존재하는 매물 이름입니다."),
    EMPTY_SALE_NO(400,"SA_003", "매물의 번호는 공백일 수 없습니다."),
    INVALID_SALE_NO(400,"SA_004", "번호는 1이상이어야 합니다."),

    NOT_FOUND_DETAIL(404, "DE_001", "존재하지 않는 납부 내역입니다."),
    DUPLICATED_DETAIL_NAME(400, "DE_002", "이미 존재하는 납부 내역 이름입니다."),
    EMPTY_DETAIL_NO(400,"DE_003", "납부 내역의 번호는 공백일 수 없습니다."),
    INVALID_DETAIL_NO(400,"DE_004", "번호는 1이상이어야 합니다."),

    NOT_FOUND_BUSINESS(404, "BU_001", "존재하지 않는 거래입니다."),
    DUPLICATED_BUSINESS_NAME(400, "BU_002", "이미 존재하는 거래 이름입니다."),
    EMPTY_BUSINESS_NO(400,"BU_003", "거래의 번호는 공백일 수 없습니다."),
    INVALID_BUSINESS_NO(400,"BU_004", "번호는 1이상이어야 합니다."),

    NOT_FOUND_REVIEW(404, "RE_001", "존재하지 않는 리뷰입니다."),
    DUPLICATED_REVIEW_NAME(400, "RE_002", "이미 존재하는 리뷰 이름입니다."),
    EMPTY_REVIEW_NO(400,"RE_003", "리뷰의 번호는 공백일 수 없습니다."),
    INVALID_REVIEW_NO(400,"RE_004", "번호는 1이상이어야 합니다."),

    DUPLICATED_EMAIL(400, "AU_002", "이미 존재하는 E-mail입니다."),
    NOT_FOUND_USER(404, "AU_001", "존재하지 않는 회원입니다."),
    EMPTY_USER_NO(400,"RE_003", "회원의 번호는 공백일 수 없습니다."),
    INVALID_USER_NO(400,"RE_004", "번호는 1이상이어야 합니다."),

    INTERNAL_SERVER_ERROR(500, "SYS_001", "내부 서버 오류입니다."),
    BAD_REQUEST(400, "SYS_002", "잘못된 요청입니다."),
    SERVICE_UNAVAILABLE(503, "SYS_003", "서비스가 일시적으로 이용 불가능합니다."),
    DATABASE_ERROR(500, "SYS_004", "데이터베이스 오류가 발생했습니다."),
    DATA_INTEGRITY_VIOLATION(409, "SYS_005", "데이터 무결성 위반입니다."),
    METHOD_NOT_ALLOWED(405, "SYS_006", "허용되지 않은 메서드입니다."),
    UNSUPPORTED_MEDIA_TYPE(415, "SYS_007", "지원하지 않는 미디어 타입입니다."),
    RATE_LIMIT_EXCEEDED(429, "SYS_008", "요청 제한을 초과했습니다.");

    private final int status;
    private final String code;
    private final String message;
}
