package com.xList.dao.repository;

import com.xList.dao.CRUDrepository.NoteDao;
import com.xList.dao.entities.Note;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class NoteImplementatoin implements NoteDao {


    @Override
    public void addNote(Note note) {
        DataSource source = new DataSource();
        try (Connection connection = source.getConnection();
             PreparedStatement statement = (note.getId() == 0L) ? connection.prepareStatement("INSERT INTO `notes`(`note`,`note_title`,`is_archieve`,`date_added`,`color`,`user_id`) VALUES (?,?,?,?,?,?)") :
                     connection.prepareStatement("UPDATE notes SET note=?,note_title=?,is_archieve=?,date_added=?,color=?,user_id=? WHERE id=" + note.getId())) {
            statement.setString(1, note.getNoteText());
            statement.setString(2, note.getNoteTitle());
            statement.setBoolean(3, note.isArchieve());
            statement.setString(4, note.getDateAdded());
            statement.setString(5, note.getColor());
            statement.setLong(6, note.getUserId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void deleteNote(long id) {
        DataSource source = new DataSource();
        if (id > 0L) {
            try (Connection connection = source.getConnection();
                 Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM notes WHERE id =" + id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Note getNote(long user_id, long note_id) {
        DataSource source=new DataSource();

        try (Connection connection=source.getConnection();
        Statement statement=connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT * FROM notes WHERE user_id=" + user_id + " AND id="+note_id)){
            if (set.next()){
                Long idLocal=set.getLong("id");
                String note =set.getString("note");
                String note_title = set.getString("note_title");
                Boolean is_archieve =set.getBoolean("is_archieve");
                String date_added=set.getString("date_added");
                String color=set.getString("color");

                Note note1 = new Note(idLocal.longValue(),note,note_title,is_archieve.booleanValue(),date_added,color,user_id);

                return note1;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Note> getAllNote(long user_id) {
        DataSource source = new DataSource();
        List<Note> notes = new ArrayList<>();

        try (Connection connection=source.getConnection();
        Statement statement=connection.createStatement();
        ResultSet set=statement.executeQuery("SELECT * FROM notes WHERE user_id=\""+user_id+"\";")){
            while (set.next()){
                Long idLocal=set.getLong("id");
                String note = set.getString("note");
                String note_title=set.getString("note_title");
                Boolean is_archieve=set.getBoolean("is_archieve");
                String data_added=set.getString("date_added");
                String color=set.getString("color");

                Note note1=new Note(idLocal.longValue(),note,note_title,is_archieve.booleanValue(),data_added,color,user_id);

                notes.add(note1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return notes;
    }
}
