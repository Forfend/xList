package com.xList.dao.repository;

import com.xList.dao.CRUDrepository.SharedNotesDao;
import com.xList.dao.entities.SharedNotes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SharedNotesImplementation implements SharedNotesDao {

    @Override
    public void addSharedNote(SharedNotes note, long id) {
        DataSource source = new DataSource();

        try (Connection connection = source.getConnection();
             PreparedStatement statement = (note.getId() == 0L) ? connection.prepareStatement("INSERT INTO `shared_notes`(`id`,`user_id`,`notes_id`) VALUES (?,?,?)") :
                     connection.prepareStatement("UPDATE shared_notes SET id=?,user_id=?,notes_id=? WHERE id=" + note.getId())) {
            statement.setLong(1, note.getId());
            statement.setLong(2, note.getUserId());
            statement.setLong(3, note.getNotesId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteSharedNote(long id) {
        DataSource source = new DataSource();
        if (id > 0L) {
            try (Connection connection = source.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM shared_notes WHERE id=" + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SharedNotes getSharedNotes(long userId, long noteId) {
        DataSource source = new DataSource();

        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT * FROM shared_notes WHERE user_id=" + userId + " AND notes_id=" + noteId)) {
            Long idSharedNote = set.getLong("id");
            Long userIdBySharedNote = set.getLong("user_id");
            Long notesIdBySharedNote = set.getLong("notes_id");

            SharedNotes sharedNotes = new SharedNotes(idSharedNote, userIdBySharedNote, notesIdBySharedNote);

            return sharedNotes;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<SharedNotes> getAllSharedNotes(long userId) {
        DataSource source = new DataSource();
        List<SharedNotes> notes = new ArrayList<>();

        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT * FROM shared_notes WHERE user_id=" + userId)) {
            while (set.next()) {
                Long idSharedNote = set.getLong("id");
                Long userIdBySharedNote = set.getLong("user_id");
                Long notesIdBySharedNote = set.getLong("notes_id");

                SharedNotes sharedNotes = new SharedNotes(idSharedNote, userIdBySharedNote, notesIdBySharedNote);

                notes.add(sharedNotes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notes;
    }
}
