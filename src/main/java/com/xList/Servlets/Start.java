package com.xList.Servlets;


import com.xList.service.IndexTemplate;
import com.xList.view.PageParts;
import com.xList.views.IndexHtmlView;
import com.xList.views.NoteHtmlViews;
import com.xList.views.PathHtml;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Start", value = {"/*"}, loadOnStartup = 1)
public class Start extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        IndexTemplate indexTemplate=new IndexTemplate(out);
        HttpSession session=request.getSession();
        if (indexTemplate.checkLogin(request,session)){
            response.sendRedirect("/");
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
            default:
                response.sendRedirect("/");
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();

        PathHtml pathHtml = PathHtml.getInstance();

        if (pathHtml.getPath().equals("")){
            pathHtml.setPath(getServletContext().getRealPath("/html/"));
        }

        NoteHtmlViews.getInstance();
        IndexHtmlView.getInstance();
    }
}
