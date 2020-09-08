package lihan.exception;

public class BusinessException extends BaseException {
    public BusinessException(String code, String message) {
        super("BUS"+code, message);
    }

    public BusinessException(String code, String message, Throwable cause) {
        super("BUS"+code, message, cause);
    }
}
