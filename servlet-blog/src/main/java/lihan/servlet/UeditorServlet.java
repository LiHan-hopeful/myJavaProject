package lihan.servlet;

import lihan.uti.MyActionEnter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

@WebServlet("/ueditor")
public class UeditorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        URL url = UeditorServlet.class.getClassLoader().getResource("config.json");
        String path = URLDecoder.decode(url.getPath(), "UTF-8");
        MyActionEnter enter = new MyActionEnter(req, path);
        String exec = enter.exec();
        resp.getWriter().write(exec);

    }
}
