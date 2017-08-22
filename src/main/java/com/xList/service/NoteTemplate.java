package com.xList.service;


import com.xList.dao.CRUDrepository.NoteDao;
import com.xList.dao.CRUDrepository.SharedNotesDao;
import com.xList.dao.CRUDrepository.UserDao;
import com.xList.dao.entities.Note;
import com.xList.dao.entities.SharedNotes;
import com.xList.dao.entities.User;
import com.xList.dao.repository.NoteImplementatoin;
import com.xList.dao.repository.SharedNotesImplementation;
import com.xList.dao.repository.UserImplementation;
import com.xList.views.NoteHtmlViews;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class NoteTemplate {

    private Logger logger = Logger.getLogger("com.xList.dao.repository");

    private PrintWriter out;

    public NoteTemplate(PrintWriter out) {
        this.out = out;
    }

    public void showShortNotes(List<Note> notes) {
        if (out == null) return;

        out.println("<div class=\"row\">");
        for (Note oneNote : notes) {
            String htmlNote = NoteHtmlViews.getInstance().getShortNoteView();
            htmlNote = htmlNote.replace("<!--insert-id-here-->", String.valueOf(oneNote.getId()));
            htmlNote = htmlNote.replace("<!--insert-title-here-->", String.valueOf(oneNote.getNoteTitle()));
            String shortstr = oneNote.getNoteText().length() < 100 ? oneNote.getNoteText() : oneNote.getNoteText().substring(0, 100);
            htmlNote = htmlNote.replace("<!--insert-memo-here-->", shortstr);
            htmlNote = htmlNote.replace("/*insert-color-here*/", "#" + oneNote.getColor());
            out.println(htmlNote);
        }
        out.println("</div>");
    }

    public void showAddForm() {
        if (out == null) return;
        out.println(NoteHtmlViews.getInstance().getNoteAddForm());
    }

    public void showEditForm(Note note) {
        if (out == null) return;

        String aform = NoteHtmlViews.getInstance().getNoteEditForm();
        aform = aform.replace("insert-id-here", String.valueOf(note.getId()));
        aform = aform.replace("insert-title-here", String.valueOf(note.getNoteTitle()));
        aform = aform.replace("insert-note-here", String.valueOf(note.getNoteText()));
        switch (note.getColor()) {
            case "FFFFFF":
                aform = aform.replace("checked1", "checked");
                break;
            case "FF8A80":
                aform = aform.replace("checked2", "checked");
            case "FCD287":
                aform = aform.replace("checked3", "checked");
                break;
            case "FFFF8D":
                aform = aform.replace("checked4", "checked");
                break;
            case "CFD8DC":
                aform = aform.replace("checked5", "checked");
                break;
            case "AFE5FD":
                aform = aform.replace("checked6", "checked");
                break;
            case "A7FFEB":
                aform = aform.replace("checked7", "checked");
                break;
            case "CCFF90":
                aform = aform.replace("checked8", "checked");
                break;
        }

        for (int i = 0; i < 9; i++) {
            aform = aform.replace("checked" + i, "");
        }
        out.println(aform);
    }

    /**
     * Search notes
     * @param request object from the servlet o get search text from the search form
     * @return list of found notes or null if there are note
     */

    public List<Note> getSearchNotes(HttpServletRequest request, HttpSession session) {
        try {
            Long user_id = (Long) session.getAttribute("user_id");
            if (user_id == null) return null;

            String searchText = new String(request.getParameter("searchText").getBytes("iso-8859-1"),
                    "UTF-8");
            logger.fine("search text " + searchText + " user_id " + user_id);

            NoteDao noteDao = new NoteImplementatoin();
            return noteDao.searchByNoteTitleByNote(searchText, user_id);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Finds user and output in HTML page
     * @param request from the servlet
     */

    public void getSharingNotes(HttpServletRequest request, HttpSession session) {
        if (request == null) return;
        logger.fine("getSharingNotes\t");
        long noteId = Long.parseLong(request.getParameter("noteIdSearch"));
        long userId = Long.parseLong(session.getAttribute("user_id").toString());

        NoteDao noteDao = new NoteImplementatoin();
        Note note = noteDao.getNote(userId,noteId);
        logger.fine(note.toString());
        this.showEditForm(note);

        try {
            String searchText = new String(request.getParameter("search-sharing-user").getBytes("iso-8859-1"),"UTF-8");
            UserDao userDao = new UserImplementation();
            List<User> users = userDao.findByUsernameByName(searchText);
            if (users == null) return;
            logger.fine("founded users\t"+users.size());
            out.println("<div class=\"row\">");
            out.println("<div class=\"col-xs-12 col-sm-12 col-md-6 col-md-offset-3 col-lg-6 col-lg-offset-3\">");
            out.println("<ul class=\"list-group\">\n\t\t");

            String resultString = users.stream()
                    .map(u-> "<a class=\"list-group-item\" " +
                            "href=\"/note/sharing-user-add-note?userid=" + u.getId() + "&noteid=" + noteId  + "\" >" +
                            "<span class=\"glyphicon glyphicon-user\" aria-hidden=\"true\"></span>&nbsp;&nbsp;" +
                            u.getName() + "\t" +
                            "&nbsp;&nbsp;<span class=\"glyphicon glyphicon-envelope\" aria-hidden=\"true\"></span>&nbsp;&nbsp;" +
                            u.getUsername() +
                            "<span class=\"glyphicon glyphicon-plus pull-right\" aria-hidden=\"true\"></span>" +
                            "</a>")
                    .collect(Collectors.joining("\n"));
            out.println(resultString);
            out.println("\n\t\t</ul>\n\t\t</div>\n</div>");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void addSharingUser(HttpServletRequest request){
        long userId = Long.parseLong(request.getParameter("userid"));
        long noteId = Long.parseLong(request.getParameter("noteid"));
        SharedNotesDao sharedNotesDao = new SharedNotesImplementation();
        SharedNotes sharedNotes = new SharedNotes(userId,noteId);
        sharedNotesDao.addSharedNote(sharedNotes);
    }

}
