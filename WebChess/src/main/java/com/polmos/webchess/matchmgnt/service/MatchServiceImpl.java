package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.dao.MatchDAO;
import com.polmos.webchess.enums.GameStatus;
import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.entity.User;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RobicToNieMaKomu
 */
@Service("matchService")
public class MatchServiceImpl implements MatchService {
    
    @Autowired
    private MatchDAO matchDAO;

    @Override
    public List<Match> findAllMatches() {
        List<Match> result = new ArrayList<>();
        return result;
    }

    @Override
    public void startNewMatch(User wplayer, User bplayer, Integer gameTime) {
        Match match = new Match();
        match.setWplayer(wplayer);
        match.setBplayer(bplayer);
        match.setWplayerTime(gameTime);
        match.setBplayerTime(gameTime);
        match.setProgress(GameStatus.WHITE_MOVE);
        match.setHasended(false);
        matchDAO.saveMatch(match);
    }
}
