package servlet;

import dao.BuildingDAO;
import model.Building;
import util.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/building/update")
public class BuildingUpdateServlet extends AbstractBaseServlet{
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Building b = JSONUtil.read(req.getInputStream(),Building.class);
        int num = BuildingDAO.update(b);
        return null;
    }
}
