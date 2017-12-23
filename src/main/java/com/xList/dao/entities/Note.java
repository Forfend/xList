package com.xList.dao.entities;


public class Note {

    private long id;
    private String noteText;
    private String noteTitle;
    private boolean isArchieve;
    private String dateAdded;
    private String color;
    private long userId;

    public Note(long id, String noteText, String noteTitle, boolean isArchieve, String dateAdded, String color, long userId) {
        this.id = id;
        this.noteText = noteText;
        this.noteTitle = noteTitle;
        this.isArchieve = isArchieve;
        this.dateAdded = dateAdded;
        this.color = color;
        this.userId = userId;
    }

    public Note(String noteText, String noteTitle, boolean isArchieve, String dateAdded, String color, long userId) {
        this.id=0L;
        this.noteText = noteText;
        this.noteTitle = noteTitle;
        this.isArchieve = isArchieve;
        this.dateAdded = dateAdded;
        this.color = color;
        this.userId = userId;
    }

    public Note() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public boolean isArchieve() {
        return isArchieve;
    }

    public void setArchieve(boolean archieve) {
        isArchieve = archieve;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", noteText='" + noteText + '\'' +
                ", noteTitle='" + noteTitle + '\'' +
                ", isArchieve=" + isArchieve +
                ", dateAdded='" + dateAdded + '\'' +
                ", color='" + color + '\'' +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        if (id != note.id) return false;
        if (isArchieve != note.isArchieve) return false;
        if (userId != note.userId) return false;
        if (noteText != null ? !noteText.equals(note.noteText) : note.noteText != null) return false;
        if (noteTitle != null ? !noteTitle.equals(note.noteTitle) : note.noteTitle != null) return false;
        if (dateAdded != null ? !dateAdded.equals(note.dateAdded) : note.dateAdded != null) return false;
        return color != null ? color.equals(note.color) : note.color == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (noteText != null ? noteText.hashCode() : 0);
        result = 31 * result + (noteTitle != null ? noteTitle.hashCode() : 0);
        result = 31 * result + (isArchieve ? 1 : 0);
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        return result;
    }
}
