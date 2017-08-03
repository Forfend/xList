package com.xList.dao.CRUDrepository;

import com.xList.dao.entities.Note;

import java.util.List;

public interface NoteDao {

    void addNote(Note note);

    void deleteNote(long id);

    Note getNote(long user_id, long note_id);

    List<Note> getAllNote(long user_id);
}
