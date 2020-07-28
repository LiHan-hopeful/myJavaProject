package lihan.servlet;

import lihan.exception.BaseException;
import lihan.model.Result;
import lihan.uti.JSONUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractBaseServlet extends HttpServlet {

    private  static final ConcurrentMap<String, AtomicInteger> statistics = new ConcurrentHashMap<>();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=UTF-8");

        Result result = new Result();
        try {
            Object data = process(req, resp);
            result.setSuccess(true);
            result.setData(data);
        }catch (Exception e){
            if(e instanceof BaseException){
                BaseException be = (BaseException) e;
                result.setMessage("错误码："+ be.getCode() +", 错误信息："+ be.getMessage());
            }else{
                result.setMessage("服务器异常： 未知的错误");
            }
            StringWriter sw = new StringWriter();
            PrintWriter epw = new PrintWriter(sw);
            e.printStackTrace(epw);
            result.setStackTrace(sw.toString());
        }
        PrintWriter pw = resp.getWriter();
        pw.println(JSONUtil.serialize( result));
        pw.flush();

        String path = req.getServletPath();
        AtomicInteger count = statistics.putIfAbsent(path, new AtomicInteger(1));
       if(count != null) {
           count.incrementAndGet();
       }
    }

    public abstract Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception ;

    public static ConcurrentMap<String, AtomicInteger> getStatistics() {
        return statistics;
    }
}
