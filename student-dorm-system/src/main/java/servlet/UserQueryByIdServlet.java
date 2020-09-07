package servlet;

import dao.UserDAO;
import model.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/queryById")
public class UserQueryByIdServlet extends AbstractBaseServlet{

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        User u = UserDAO.queryById(Integer.parseInt(id));
        return u;
    }
}
