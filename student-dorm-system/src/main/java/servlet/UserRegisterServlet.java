package servlet;

import dao.UserDAO;
import model.User;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user/register")
public class UserRegisterServlet extends AbstractBaseServlet {

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User u = JSONUtil.read(req.getInputStream(), User.class);
        int num = UserDAO.insert(u);
        return null;
    }

}
