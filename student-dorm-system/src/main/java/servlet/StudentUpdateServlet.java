package servlet;

import dao.StudentDAO;
import model.Student;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/student/update")
public class StudentUpdateServlet extends AbstractBaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Student s = JSONUtil.read(req.getInputStream(), Student.class);
        int num = StudentDAO.update(s);
        return null;
    }
}
