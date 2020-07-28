package lihan.servlet;

import lihan.dao.ArticleDAO;
import lihan.exception.BusinessException;
import lihan.model.Article;
import lihan.uti.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@WebServlet("/articleUpdate")
public class ArticleUpdateServlet extends AbstractBaseServlet{

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        InputStream is = req.getInputStream();
        Article article =  JSONUtil.deserialize(is, Article.class);
//        System.out.println("=======================================\n"+article);
        int num = ArticleDAO.update(article);
        if(num != 1){
            throw new BusinessException("004", "文章修改出错。");
        }
        return null;
    }
}
