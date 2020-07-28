package lihan.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lihan.model.Article;
import org.junit.Test;

import java.util.Date;

public class JSONUtilTest {

    @Test
    public void t1() {
        try {
            ObjectMapper  mapper = new ObjectMapper();
            Article article = new Article();
            article.setId(1);
            article.setTitle("江湖");
            article.setContent("寒江孤影，江湖故人，相逢何必曾相识");
            article.setUserId(1);
            article.setCreateTime(new Date());
            String s = mapper.writeValueAsString(article);
            System.out.println(s);
            Article des = mapper.readValue(s, Article.class);
            System.out.println(des);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
