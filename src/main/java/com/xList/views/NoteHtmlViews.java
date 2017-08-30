package com.xList.views;

public class NoteHtmlViews {
    private static NoteHtmlViews instance;

    private final String shortNoteView;
    private final String noteAddForm;
    private final String noteEditForm;
    private final String noteAddButton;

    public static NoteHtmlViews getInstance(){
        if (instance==null){
            instance=new NoteHtmlViews();
        }

        return instance;
    }

    private NoteHtmlViews(){
        PathHtml pathHtml=PathHtml.getInstance();
        this.shortNoteView=pathHtml.getPartialHtml("short-note-view.html");
        this.noteAddForm=pathHtml.getPartialHtml("note-add-form.html");
        this.noteEditForm=pathHtml.getPartialHtml("note-edit-form.html");
        this.noteAddButton=pathHtml.getPartialHtml("note-add-button.html");

    }

    public String getShortNoteView() {
        return shortNoteView;
    }

    public String getNoteAddForm() {
        return noteAddForm;
    }

    public String getNoteEditForm() {
        return noteEditForm;
    }

    public String getNoteAddButton() {return noteAddButton; }
}
