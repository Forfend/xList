package com.xList.Servlets;

import com.xList.Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "Start", value = {"/*"})
public class Start extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String emailLogin = new String(request.getParameter("emailLogin").getBytes("iso-8859-1"),
                "UTF-8");
        String loginPassword = new String(request.getParameter("loginPassword").getBytes("iso-8859-1"),
                "UTF-8");
        User user = new User(emailLogin, loginPassword);
        HttpSession session = request.getSession();
        if(user.checkLogin()) {
            session.setAttribute("username", user.getUsername());
            response.sendRedirect("/");
        } else {
            out.write("<H2 class=\"text-danger\">Помилка авторизації!</H2>");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        switch (request.getPathInfo()) {
            case "/":
                if(session.getAttribute("username") != null) {
                    out.write("<H2 class=\"text-success\">Починаємо роботу...</H2>");
                } else {
                    out.write(PageParts.getPartialHtml(getServletContext().getRealPath("/html/login-form.html")));
                }
                break;
            case "/logout":
                session.removeAttribute("username");
                session.removeAttribute("memo");
                response.sendRedirect("/");
                break;
        }

    }
}
