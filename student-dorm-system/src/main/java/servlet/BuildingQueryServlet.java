package servlet;

import dao.BuildingDAO;
import model.Building;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/building/query")
public class BuildingQueryServlet extends AbstractBaseServlet{


    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<Building> buildings = BuildingDAO.querys();
        return buildings;
    }
}
