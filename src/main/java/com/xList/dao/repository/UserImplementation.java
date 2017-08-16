package com.xList.dao.repository;

import com.xList.dao.CRUDrepository.UserDao;
import com.xList.dao.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserImplementation implements UserDao {

    private Logger logger = Logger.getLogger("com.xList.dao.repository");

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
        DataSource source = new DataSource();

        try (Connection connection = source.getConnection();
             PreparedStatement statement = (user.getId() == 0L) ? connection.prepareStatement("INSERT INTO user (username, password, name) VALUES (?,?,?)") :
                     connection.prepareStatement("UPDATE user SET username=?, password=?, name=? WHERE id=" + user.getId())) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findByUsernameByName(String search) {
        logger.fine("findByUsernameByName");

        DataSource source = new DataSource();
        List<User> users = new ArrayList<>();
        try (Connection connection = source.getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT DISTINCT id, username, password, name FROM user WHERE (username LIKE \"%" + search + "%\" OR name LIKE \"%" + search + "%\")LIMIT 10")) {

            while (set.next()) {
                User user = new User(
                        set.getLong(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4)
                );
                users.add(user);
            }
            logger.fine("founded users\t" + users.size());
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
