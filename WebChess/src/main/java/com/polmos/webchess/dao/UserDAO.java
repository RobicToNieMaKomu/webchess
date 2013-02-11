package com.polmos.webchess.dao;

import com.polmos.webchess.matchmgnt.entity.User;
import java.util.List;

/**
 *
 * @author Piotrek
 */
public interface UserDAO {
    List<User> getAllUsers();
    User findUserById(Integer id);
    User findUserByName(String userName);
    void saveUser(User user);
    void updateUser(User user);
    void removeUser(User user);
}
