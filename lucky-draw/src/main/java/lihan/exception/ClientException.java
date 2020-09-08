package lihan.exception;

public class ClientException extends BaseException {
    public ClientException(String code, String message) {
        super("CLI"+code, message);
    }

    public ClientException(String code, String message, Throwable cause) {
        super("CLI"+code, message, cause);
    }
}
