package servlet;

import dao.StudentDAO;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student/delete")
public class StudentDeleteServlet extends AbstractBaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //相同的key有多个，可以获取到value数组
        String[] ids = req.getParameterValues("ids");
        int num = StudentDAO.delete(ids);
        return null;
    }
}
