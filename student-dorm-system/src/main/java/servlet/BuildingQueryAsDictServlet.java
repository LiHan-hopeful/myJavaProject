package servlet;

import dao.BuildingDAO;
import model.DictionaryTag;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/building/queryAsDict")
public class BuildingQueryAsDictServlet extends AbstractBaseServlet {
    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        List<DictionaryTag> tags = BuildingDAO.query();
        return tags;
    }
}
