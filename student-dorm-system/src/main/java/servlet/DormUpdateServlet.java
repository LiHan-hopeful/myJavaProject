package servlet;

import dao.DormDAO;
import model.Dorm;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dorm/update")
public class DormUpdateServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Dorm d = JSONUtil.read(req.getInputStream(), Dorm.class);
        int num = DormDAO.update(d);
        return null;
    }
}
