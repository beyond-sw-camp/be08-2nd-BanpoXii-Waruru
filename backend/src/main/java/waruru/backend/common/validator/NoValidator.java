package waruru.backend.common.validator;

import waruru.backend.common.exception.ErrorCode;
import waruru.backend.common.exception.InvalidParameterException;

public class NoValidator {

    public static void isValidUserNo(Long userNo) {
        if (userNo == null) {
            throw new InvalidParameterException(ErrorCode.EMPTY_USER_NO);
        }
        if (userNo <= 0) {
            throw new InvalidParameterException(ErrorCode.INVALID_USER_NO);
        }
    }

    public static void isValidSaleNo(Long saleNo) {
        if (saleNo == null) {
            throw new InvalidParameterException(ErrorCode.EMPTY_SALE_NO);
        }
        if (saleNo <= 0) {
            throw new InvalidParameterException(ErrorCode.INVALID_SALE_NO);
        }
    }

    public static void isValidDetailNo(Long detailNo) {
        if(detailNo == null) {
            throw new InvalidParameterException(ErrorCode.EMPTY_DETAIL_NO);
        }
        if(detailNo <= 0) {
            throw new InvalidParameterException(ErrorCode.INVALID_DETAIL_NO);
        }
    }

    public static void isValidReviewNo(Long reviewNo) {
        if(reviewNo == null) {
            throw new InvalidParameterException(ErrorCode.EMPTY_REVIEW_NO);
        }
        if(reviewNo <= 0) {
            throw new InvalidParameterException(ErrorCode.INVALID_REVIEW_NO);
        }
    }
}
