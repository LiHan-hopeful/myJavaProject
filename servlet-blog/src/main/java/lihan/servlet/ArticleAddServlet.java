package lihan.servlet;

import lihan.dao.ArticleDAO;
import lihan.exception.BusinessException;
import lihan.model.Article;
import lihan.uti.JSONUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

@WebServlet("/articleAdd")
public class ArticleAddServlet extends AbstractBaseServlet {

    @Override
    public Object process(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        InputStream is = req.getInputStream();
        Article article =  JSONUtil.deserialize(is, Article.class);
        //模拟
//        System.out.println("=======================================\n"+article);
        int num = ArticleDAO.insert(article);
        if(num != 1){
            throw new BusinessException("001", "插入文章错误");
        }
        return null;
    }
}
