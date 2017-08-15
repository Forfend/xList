package com.xList.dao.CRUDrepository;

import com.xList.dao.entities.SharedNotes;

import java.util.List;

public interface SharedNotesDao {

    void addSharedNote(SharedNotes note, long id);

    void deleteSharedNote(long id);

    SharedNotes getSharedNotes(long userId,long noteId);

    List<SharedNotes> getAllSharedNotes(long userId);
}
