package lihan.exception;

public class SystemException extends BaseException {
    public SystemException(String code, String message) {
        super("SYS"+code, message);
    }

    public SystemException(String code, String message, Throwable cause) {
        super("SYS"+code, message, cause);
    }
}
