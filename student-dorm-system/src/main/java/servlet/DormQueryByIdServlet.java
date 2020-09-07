package servlet;

import dao.DormDAO;
import model.Dorm;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dorm/queryById")
public class DormQueryByIdServlet extends AbstractBaseServlet{

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        Dorm d = DormDAO.queryById(Integer.parseInt(id));
        return d;
    }
}
