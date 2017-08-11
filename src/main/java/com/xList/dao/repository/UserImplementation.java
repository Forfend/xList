package com.xList.dao.repository;

import com.xList.dao.CRUDrepository.UserDao;
import com.xList.dao.entities.User;

import java.sql.*;

public class UserImplementation implements UserDao {

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
        }
    }
}