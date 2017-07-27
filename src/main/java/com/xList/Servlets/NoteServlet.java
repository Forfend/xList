package com.xList.Servlets;

import com.xList.Model.Note;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "NoteServlet",value = {"/note/*"})
public class NoteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        out.println(PageParts.getTag("h3","Додано!","class=\"text-info\""));
        String memo = new String(request.getParameter("textInputMemo").getBytes("iso-8859-1"),
                "UTF-8");
        String[]memoList=memo.split("\\n");
        for (String line:memoList){
            out.println(PageParts.getTag("h4","Ви додали:\t"+line,""));
        }
        HttpSession session=request.getSession();

        Note note = new Note(memoList);
        session.setAttribute("memo", memoList);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        switch (request.getPathInfo()){
            case "/info":
                out.println(PageParts.getTag("div",showInfo(request),"class=\"row\""));
                break;
            case "/add":
                out.println(PageParts.getPartialHtml(getServletContext().getRealPath("/html/note-add.html")));
                break;
            case "/show":
                out.println(showNote(request));
                break;
        }
    }

    private String showInfo(HttpServletRequest request){
            return PageParts.getTag("h1","Інформація","")+
                    PageParts.getTag("h2", "requestURI = contextPath + servletPath + pathInfo", "") +
                    PageParts.getTag("h3", "getContextPath:\t" + request.getContextPath(), "") + "\n" +
                    PageParts.getTag("h3", "getServletPath:	" + request.getServletPath(), "") + "\n" +
                    PageParts.getTag("h3", "getPathInfo:	" + request.getPathInfo(), "") +  "\n";
    }

    private String showNote(HttpServletRequest request){
        HttpSession session=request.getSession();
        String []list= (String[]) session.getAttribute("memo");
        StringBuilder note = new StringBuilder();
        if (list!=null){
            for (String row: list){
                note.append(PageParts.getTag("p",row,""));
            }
        }
        return note.toString();
    }
}
