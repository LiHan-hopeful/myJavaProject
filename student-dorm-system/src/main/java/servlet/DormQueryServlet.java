package servlet;

import dao.DormDAO;
import dao.UserDAO;
import model.Dorm;
import model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/dorm/query")
public class DormQueryServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Dorm> dorms = DormDAO.querys();
        return dorms;
    }
}
