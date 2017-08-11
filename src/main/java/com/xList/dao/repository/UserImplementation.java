package com.xList.dao.repository;

import com.xList.dao.CRUDrepository.UserDao;
import com.xList.dao.entities.User;
import com.xList.loger.SaveLogError;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class UserImplementation implements UserDao {

    private SaveLogError show = e -> {
        System.out.println(e);
        System.out.println(LocalDate.now());
    };

    private SaveLogError save = e -> {
        Path file = Paths.get("E:/Project/xList/LogOfError.txt").toAbsolutePath();
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
    public User findByUsername(String username) {
        DataSource source = new DataSource();

        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT id,username,password,name FROM user WHERE user.username=\"" + username + "\";")) {
            if (set.next()) {
                User user = new User();
                user.setId(set.getLong("id"));
                user.setUsername(set.getString("username"));
                user.setPassword(set.getString("password"));
                user.setName(set.getString("name"));
                System.out.println(user);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            show.saveAndShowError(e);
            save.saveAndShowError(e);
        }
        return null;
    }

    @Override
    public void registerUser(User user) {
        DataSource source=new DataSource();

        try (Connection connection=source.getConnection();
        PreparedStatement statement = (user.getId() == 0L) ? connection.prepareStatement("INSERT INTO user (username, password, name) VALUES (?,?,?)") :
        connection.prepareStatement("UPDATE user SET username=?, password=?, name=? WHERE id=" + user.getId())){
            statement.setString(1,user.getUsername());
            statement.setString(2,user.getPassword());
            statement.setString(3,user.getName());

            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
            show.saveAndShowError(e);
            save.saveAndShowError(e);
        }
    }
}
