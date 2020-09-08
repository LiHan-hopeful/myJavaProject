package lihan.config;

import lihan.base.ResponseResult;
import lihan.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

@Slf4j//lombok定义的日志注解：可以使用log变量，来记录日志
@ControllerAdvice
public class ExceptionAdvisor {


    /**
     * 请求数据错误：包括类型转换错误，校验失败
     * @param e
     */
    @ExceptionHandler({
            BindException.class//使用@Valid 验证路径中请求实体校验失败后抛出的异常
            , ConstraintViolationException.class//处理请求参数格式错误 @RequestParam上validate失败后抛出的异常
            , MethodArgumentNotValidException.class//处理请求参数格式错误 @RequestBody上validate失败后抛出的异常
            , MethodArgumentTypeMismatchException.class//请求参数类型转换错误
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)//400
    public void handleMethodArgumentTypeMismatchException(Throwable e){
        log.debug("================================");
        log.debug("Controller方法参数类型转换错误", e);
    }

    @ExceptionHandler({
            MethodNotAllowedException.class
            , HttpRequestMethodNotSupportedException.class
    })
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)//405
    public void handleMethodNotAllowedException(Throwable e){
        log.debug("================================");
        log.debug("Controller提供的http方法不支持", e);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)//响应状态码404，响应体为null
    public void handleNoHandlerFoundException(Throwable e){
        log.debug("================================");
        log.debug("找不到http请求处理器", e);
    }

    /**
     * SpringMVC框架，在调用Controller方法时，类似这样的处理方式：
     * try{
     *     controller.方法调用();
     * }catch(){===>@ExceptionHandler需要捕获的异常类
     *     调用方法
     * }
     * 日志的使用：
     * 日志级别由低到高：trace,debug,info,warn,error
     * 根据配置的日志打印级别，调用方法>=配置的级别，就会打印
     */
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.OK)//返回响应状态码200
    @ResponseBody
    public Object handleBaseException(BaseException e){
        log.debug("================================");
        log.debug("服务端自定义异常", e);//打印错误信息+异常对象的堆栈信息
        return ResponseResult.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handleException(Throwable e){
        log.error("================================");
        log.error("未知异常", e);
        return ResponseResult.error();
    }
}
