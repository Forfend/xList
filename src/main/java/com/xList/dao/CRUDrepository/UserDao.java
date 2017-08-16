package com.xList.dao.CRUDrepository;

import com.xList.dao.entities.User;

import java.util.List;

public interface UserDao {

    User findByUsername(String username);

    void registerUser(User user);

    List<User> findByUsernameByName(String search);
}
