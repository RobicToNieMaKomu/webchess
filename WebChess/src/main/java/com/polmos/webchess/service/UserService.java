package com.polmos.webchess.service;

import com.polmos.webchess.matchmgnt.entity.User;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface UserService {
    
    User findUserByName(String userName);
}
