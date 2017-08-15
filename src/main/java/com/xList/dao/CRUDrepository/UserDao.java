package com.xList.dao.CRUDrepository;

import com.xList.dao.entities.User;

public interface UserDao {

    User findByUsername(String username);

    void registerUser(User user);
}
