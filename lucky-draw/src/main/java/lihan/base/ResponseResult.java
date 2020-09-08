package lihan.base;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseResult {

    /**
     * 定义异常，返回给前端响应状态码是200
     * 前端可以通过success字段，判断是显示正确的业务数据，还是错误码+错误信息
     * 前端：
     * if(响应数据.success){
     *     显示data字段的数据
     * }else{
     *     显示code错误码+message错误信息
     * }
     */
    private boolean success;
    private Object data;
    private String code;
    private String message;

    private ResponseResult(){}
    //正常返回业务数据
    public static ResponseResult ok(Object o){
        ResponseResult r = new ResponseResult();
        r.success = true;
        r.data = o;
        return r;
    }
    //出现异常时，返回的数据
    public static ResponseResult error(){
        return error("ERR000000", "未知的错误，请联系管理员");
    }
    public static ResponseResult error(String code, String message){
        ResponseResult r = new ResponseResult();
        r.code = code;
        r.message = message;
        return r;
    }
}
