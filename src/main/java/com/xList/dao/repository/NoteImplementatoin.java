package com.xList.dao.repository;

import com.xList.dao.CRUDrepository.NoteDao;
import com.xList.dao.entities.Note;
import com.xList.loger.SaveLogError;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class NoteImplementatoin implements NoteDao {

    private SaveLogError show = e -> {
        System.out.println(e);
        System.out.println(LocalDate.now());
    };

    private SaveLogError save = e -> {
        Path file = Paths.get("xList/LogOfError.txt").toAbsolutePath();
        Charset charset = Charset.forName("UTF-8");
        BufferedWriter writer = null;
        try {
            writer= Files.newBufferedWriter(file,charset,APPEND,CREATE);
            String message = e.toString();
            writer.write(message,0,message.length());
            writer.write(LocalDate.now().toString());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    };

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
            show.saveAndShowError(e);
            save.saveAndShowError(e);
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
                show.saveAndShowError(e);
                save.saveAndShowError(e);
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
            show.saveAndShowError(e);
            save.saveAndShowError(e);
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
            show.saveAndShowError(e);
            save.saveAndShowError(e);
        }

        return notes;
    }
}
