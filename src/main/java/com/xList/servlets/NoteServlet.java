package com.xList.servlets;

import com.xList.dao.CRUDrepository.NoteDao;
import com.xList.dao.entities.Note;
import com.xList.dao.repository.NoteImplementatoin;
import com.xList.service.NoteTemplate;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Servlet", value = {"/note/*"})
public class NoteServlet extends HttpServlet {

    private Logger logger = Logger.getLogger("com.xList.servlets");

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        switch (request.getPathInfo()){
            case "/note/add":
            case "/note/edit":
                NoteDao noteDao = new NoteImplementatoin();
                logger.fine("doPost pathInfo\t"+request.getPathInfo());
                String memo = new String(request.getParameter("textInputMemo").getBytes("iso-8859-1"),
                        "UTF-8");
                String textInputTitle = new String(request.getParameter("textInputTitle").getBytes("iso-8859-1"),
                        "UTF-8");
                String color = request.getParameter("radioitemcolor");
                if (color == null)
                    color = "FFFFFF";

                String noteId = request.getParameter("noteId");

                Note note;
                if (noteId == null) {
                    note = new Note(memo, textInputTitle, false, LocalDate.now().toString(), color,
                            Long.parseLong(session.getAttribute("user_id").toString()));
                } else {
                    note = new Note(Long.parseLong(noteId), memo, textInputTitle, false, LocalDate.now().toString(), color,
                            Long.parseLong(session.getAttribute("user_id").toString()));
                }
                noteDao.addNote(note);
                response.sendRedirect("/note/show");
                break;
            case "/note-share":
                logger.fine("/note-share\t"+request.getParameter("search-sharing-user"));
                NoteTemplate noteTemplate = new NoteTemplate(out);
                noteTemplate.getSharingNotes(request,session);
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        int user_id = Integer.parseInt(session.getAttribute("user_id").toString());
        NoteTemplate noteTemplate = new NoteTemplate(out);

        logger.fine("pathInfo\t"+ request.getPathInfo());

        NoteDao noteDao = new NoteImplementatoin();

        switch (request.getPathInfo()) {
            case "/info":
                out.println("<div class=\"row\">\n" + showInfo(request, out) + "\n</div>");
                break;
            case "/add":
                noteTemplate.showAddForm();
                break;
            case "/show":
                List<Note> notes = noteDao.getAllNote(user_id);
                noteTemplate.showShortNotes(notes);
                break;
            case "/show-note":
                long id = Long.parseLong(request.getParameter("id"));
                Note note = noteDao.getNote(user_id, id);
                noteTemplate.showEditForm(note);
                break;
            case "/delete":
                long delete = Long.parseLong(request.getParameter("id"));
                noteDao.deleteNote(delete);
                response.sendRedirect("/note/show");
                break;
            case "/search":
                List<Note> noteList = noteTemplate.getSearchNotes(request,session);
                noteTemplate.showShortNotes(noteList);
                break;
            case "/sharing-user-add-note":
                logger.fine("Sharing user id is\t"+request.getParameter("userId"));
                noteTemplate.addSharingUser(request);
                break;
            default:
                response.sendRedirect("/");
        }
    }

    private String showInfo(HttpServletRequest request, PrintWriter out) {
        return "<H1>Інформація про сервлет</H1>" +
                "<h2>requestURI = contextPath + servletPath + pathInfo</h2>" +
                "<h3>getContextPath:\t" + request.getContextPath() + "</h3>" +
                "<h3>getServletPath:\t" + request.getServletPath() + "</h3>" +
                "<h3>getPathInfo:\t" + request.getPathInfo() + "</h3>";
    }

}
