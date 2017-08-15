package com.xList.servlets;


import com.xList.service.IndexTemplate;
import com.xList.views.IndexHtmlView;
import com.xList.views.NoteHtmlViews;
import com.xList.views.PathHtml;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.*;

@WebServlet(name = "Start", value = {"/*"}, loadOnStartup = 1)
public class Start extends HttpServlet {

  //  private static Logger logger=Logger.getLogger("com.xList.servlets");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        IndexTemplate indexTemplate = new IndexTemplate(out);
        HttpSession session = request.getSession();

      //  logger.fine("pathInfo" + request.getPathInfo());
        switch (request.getPathInfo()) {
            case "/register":
                if (indexTemplate.checkRegistration(request)) {
                    response.sendRedirect("/login");
                }
                break;
            case "/login":
                if (indexTemplate.checkLogin(request, session)) {
                    response.sendRedirect("/");
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        IndexTemplate indexTemplate = new IndexTemplate(out);


        switch (request.getPathInfo()) {
            case "/":
                if (session.getAttribute("username") != null) {
                    response.sendRedirect("/note/show");
                } else {
                    indexTemplate.showLoginForm();
                }
                break;
            case "/logout":
                session.removeAttribute("username");
                session.removeAttribute("memo");
                response.sendRedirect("/");
                break;
            case "/register":
                indexTemplate.showRegisterForm();
                break;
            case "/profile-edit":
                indexTemplate.showUserProlife(session.getAttribute("username").toString());
                break;
            default:
                response.sendRedirect("/");
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();

        PathHtml pathHtml = PathHtml.getInstance();

        if (pathHtml.getPath().equals("")) {
            pathHtml.setPath(getServletContext().getRealPath("/html/"));
        }

//        try {
//            FileHandler fileHandler = new FileHandler(getServletContext().getRealPath("/com/xList/logs/logger.log"));
//            Logger.getLogger("").addHandler(fileHandler);
//            Logger.getLogger("").addHandler(new ConsoleHandler());
//            Logger.getLogger("").setLevel(Level.ALL);
//
//            Logger.getLogger("com.xList.servlets").setLevel(Level.WARNING);
//            Logger.getLogger("com.xList.dao.repository").setLevel(Level.FINE);
//        }catch (IOException e){
//            e.printStackTrace();
//        }

        NoteHtmlViews.getInstance();
        IndexHtmlView.getInstance();
    }
}
