package com.xList.dao.repository;

import com.xList.dao.CRUDrepository.SharedNotesDao;
import com.xList.dao.entities.SharedNotes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SharedNotesImplementation implements SharedNotesDao {

    @Override
    public void addSharedNote(SharedNotes note) {
        DataSource source = new DataSource();

        try (Connection connection = source.getConnection();
             PreparedStatement statement = (note.getId() == 0L) ? connection.prepareStatement("INSERT INTO `shared_notes`(`user_id`,`notes_id`) VALUES (?,?)") :
                     connection.prepareStatement("UPDATE shared_notes SET user_id=?,notes_id=? WHERE id=" + note.getId())) {
            statement.setLong(1, note.getUserId());
            statement.setLong(2, note.getNotesId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
