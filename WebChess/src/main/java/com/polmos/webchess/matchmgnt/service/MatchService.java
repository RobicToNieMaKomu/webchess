package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.entity.User;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MatchService {
    
    List<Match> findAllMatches();
    void startNewMatch(User wplayer, User bplayer, Integer gameTime);
}
