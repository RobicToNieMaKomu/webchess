package com.polmos.webchess.dao;

import com.polmos.webchess.matchmgnt.entity.Match;
import java.util.Set;

/**
 *
 * @author Piotrek
 */
public interface MatchDAO {

    Integer saveMatch(Match chat);

    Match findMatchById(Integer id);
    
    Match findMatchByTableId(Integer tableId);

    Set<Match> findAllMatchs();

    void removeMatch(Match match);
    
    Match findMatchInProgressByTableId(Integer tableID);
}
