package waruru.backend.common.exception;

public class InvalidParameterException extends BusinessException {
    public InvalidParameterException(ErrorCode errorCode) { super(errorCode); }
}
