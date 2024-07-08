package waruru.backend.common.exception;

public class DuplicatedException extends BusinessException {
    public DuplicatedException(ErrorCode errorCode) { super(errorCode); }
}
