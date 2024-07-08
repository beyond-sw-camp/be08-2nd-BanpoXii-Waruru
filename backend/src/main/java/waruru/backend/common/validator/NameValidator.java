package waruru.backend.common.validator;

import waruru.backend.common.exception.ErrorCode;
import waruru.backend.common.exception.InvalidParameterException;

public class NameValidator {

    public static void isValidSaleName(String saleName) {
        if(saleName.isEmpty()) {
            throw new InvalidParameterException(ErrorCode.EMPTY_SALE_NAME);
        }
        if(saleName.trim().isEmpty()) {
            throw new InvalidParameterException(ErrorCode.INVALID_SALE_NAME);
        }
    }

    public static void isValidDetailName(String detailTitle) {
        if(detailTitle.isEmpty()) {
            throw new InvalidParameterException(ErrorCode.EMPTY_DETAIL_NAME);
        }
        if(detailTitle.trim().isEmpty()) {
            throw new InvalidParameterException(ErrorCode.INVALID_DETAIL_NAME);
        }
    }

    public static void isValidReviewName(String title) {
        if(title.isEmpty()) {
            throw new InvalidParameterException(ErrorCode.EMPTY_REVIEW_NAME);
        }
        if(title.trim().isEmpty()) {
            throw new InvalidParameterException(ErrorCode.INVALID_REVIEW_NAME);
        }
    }
}
