package servlet;

import dao.StudentDAO;
import model.Page;
import model.Student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/student/query")
public class StudentQueryServlet extends AbstractBaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //解析searchText=&sortOrder=asc&pageSize=7&pageNumber=1
        Page p = Page.parse(req);
        List<Student> students = StudentDAO.query(p);
        return students;
    }
}
