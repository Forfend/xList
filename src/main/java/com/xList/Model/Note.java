package com.xList.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class can manage user`s note.
 */

public class Note extends ActiveRecord {

    private String[] notes;

    public Note(String[] notes) {
        this.notes = notes;
    }

    public Note() {
    }

    /**
     * Insert a note into DataBase
     * @param id - id user in the application
     */

    public void insertNote(int id) {
        StringBuilder builder = new StringBuilder();
        for (String row : notes) {
            builder.append(row).append("\n");
        }
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {
            statement.executeQuery("INSERT INTO note(note, use r_id) VALUES(\"" + builder + "\"," + id + ")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all notes from a user
     * @param id - user id in the application
     * @return notes from a user
     */
    public static ArrayList<String> getnotes(int id){
        ArrayList<String> notes = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery("SELECT note FROM note WHERE user_id=\" + id + \";");){
            while (resultSet.next()){
                notes.add(resultSet.getString(1));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return notes;
    }

    public String[] getNotes() {
        return notes;
    }

    public void setNotes(String[] notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "NoteServlet{" +
                "notes=" + Arrays.toString(notes) +
                '}';
    }
}
