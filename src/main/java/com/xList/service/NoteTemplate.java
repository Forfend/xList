package com.xList.service;

import com.xList.dao.entities.Note;
import com.xList.views.NoteHtmlViews;

import java.io.PrintWriter;
import java.util.List;

public class NoteTemplate {
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
}
