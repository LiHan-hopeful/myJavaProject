package servlet;

import dao.BuildingDAO;
import model.Building;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/building/queryById")
public class BuildingQueryByIdServlet extends AbstractBaseServlet{

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        Building b =  BuildingDAO.queryById(Integer.parseInt(id));
        return b;
    }
}
