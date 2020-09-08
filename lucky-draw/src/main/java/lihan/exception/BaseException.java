package lihan.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常的父类
 */
@Getter
@Setter
public class BaseException extends RuntimeException {

    private String code;

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
