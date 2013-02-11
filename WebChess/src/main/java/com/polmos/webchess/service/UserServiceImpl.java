package com.polmos.webchess.service;

import com.polmos.webchess.dao.UserDAO;
import com.polmos.webchess.matchmgnt.entity.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RobicToNieMaKomu
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {
    
    private final Logger logger = Logger.getLogger(UserServiceImpl.class);
    private Map<User, List<Integer>> userToTableIdsMap;
    
    @Autowired
    private UserDAO userDAO;
    
    public UserServiceImpl() {
        logger.debug("UserServiceImpl created");
        userToTableIdsMap = new HashMap<User, List<Integer>>();
    }

    @Override
    public User findUserByName(String userName) {
        User user = userDAO.findUserByName(userName);
        if (user == null) {
            logger.error("Requested user:" + userName + " does not exist");
        }
        return user;
    }

}
