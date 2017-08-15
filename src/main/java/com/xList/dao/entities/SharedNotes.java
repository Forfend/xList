package com.xList.dao.entities;

public class SharedNotes {

    private long id;
    private long userId;
    private long notesId;

    public SharedNotes(long id, long userId, long notesId) {
        this.id = id;
        this.userId = userId;
        this.notesId = notesId;
    }

    public SharedNotes(long userId, long notesId) {
        this.id=0L;
        this.userId = userId;
        this.notesId = notesId;
    }

    public SharedNotes() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getNotesId() {
        return notesId;
    }

    public void setNotesId(long notesId) {
        this.notesId = notesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SharedNotes that = (SharedNotes) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        return notesId == that.notesId;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (notesId ^ (notesId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "SharedNotes{" +
                "id=" + id +
                ", userId=" + userId +
                ", notesId=" + notesId +
                '}';
    }
}
