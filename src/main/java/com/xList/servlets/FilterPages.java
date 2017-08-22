package com.xList.servlets;


import com.xList.service.IndexTemplate;
import com.xList.views.IndexHtmlView;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

@WebFilter(filterName = "FilterPages",value = {"/*","/note/*"})
public class FilterPages implements Filter{

    private Logger logger = Logger.getLogger("com.xList.SharedServlet");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String logOutBtn = IndexHtmlView.getInstance().getLogoutButton();
        String path = ((HttpServletRequest) request).getRequestURI();
        logger.fine("path if\t"+path);
        if (path.startsWith("/noteajax/")){
            chain.doFilter(request,response);
        }else {
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter printWriter = resp.getWriter();

            String top = IndexHtmlView.getInstance().getTopHtml();
            HttpSession session = request.getSession();
            if(session.getAttribute("username") == null && (!request.getServletPath().equals(""))){
                response.sendRedirect("/");
            }

            if (session.getAttribute("username") !=null){
                logOutBtn = logOutBtn.replace("<!--username-->", session.getAttribute("username").toString());
                top = top.replace("<!-- servletInsert01 -->",logOutBtn);
            }
            printWriter.write(top);
            chain.doFilter(request,response);
            printWriter.write(IndexHtmlView.getInstance().getBottomHtml());
        }

    }

    @Override
    public void destroy() {

    }
}
