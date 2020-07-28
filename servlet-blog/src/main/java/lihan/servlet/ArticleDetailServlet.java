package lihan.servlet;

import lihan.dao.ArticleDAO;
import lihan.exception.BusinessException;
import lihan.exception.ClientException;
import lihan.model.Article;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@WebServlet("/articleDetail")
public class ArticleDetailServlet extends AbstractBaseServlet{

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String id = req.getParameter("id");
        Integer articleId;
        try {
            articleId = Integer.parseInt(id);
        } catch (Exception e) {
            throw new ClientException("001","请求参数错误[id=" + id +"]");
        }
        Article article = ArticleDAO.query(articleId);
        if(article == null){
            throw new BusinessException("003", "查询不到文章详情id="+articleId);
        }
        return article;
    }
}
