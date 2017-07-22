package com.xList.Servlets;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "FilterPages",value = {"/*","/note/*"})
public class FilterPages implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out=resp.getWriter();
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String top = PageParts.getPartialHtml(req.getServletContext().getRealPath("/html/top.html"));
        HttpSession session = request.getSession();
        if(session.getAttribute("username") == null && (!request.getServletPath().equals(""))) {
            response.sendRedirect("/");
            System.out.println("redirect");
        }
        if(session.getAttribute("username") != null) {
            String logout = PageParts.getPartialHtml(req.getServletContext().getRealPath("/html/logout-button.html"));
            top = top.replace("<!-- servletInsert01 -->", logout);
        }
        out.write(top);
        chain.doFilter(req, resp);
        out.write(PageParts.getPartialHtml(req.getServletContext().getRealPath("/html/bottom.html")));
    }

    @Override
    public void destroy() {

    }
}
