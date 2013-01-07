package com.polmos.webchess.dao;

import com.polmos.webchess.matchmgnt.entity.Match;
import java.util.Set;

/**
 *
 * @author Piotrek
 */
public interface MatchDAO {
    void saveMatch(Match chat);
     Match findMatchById(Integer id);
     Set<Match> findAllMatchs();
     void removeMatch(Match match);
}
