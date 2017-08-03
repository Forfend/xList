package com.xList.view;



public class NoteHtmlViews {
    private static NoteHtmlViews instance;

    private final String shortNoteView;
    private final String noteAddForm;

    public static NoteHtmlViews getInstance(){
        if (instance==null){
            instance=new NoteHtmlViews();
        }
        return instance;
    }

    private NoteHtmlViews(){
        PathHtml pathHtml = PathHtml.getInstance();
        this.shortNoteView = pathHtml.getPartialHtml("short-note-view.html");
        this.noteAddForm = pathHtml.getPartialHtml("note-add-form.html");
    }

    public String getShortNoteView() {
        return shortNoteView;
    }

    public String getNoteAddForm() {
        return noteAddForm;
    }
}
