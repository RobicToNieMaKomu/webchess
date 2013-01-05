package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.matchmgnt.entity.Match;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MatchService {
    
    List<Match> findAllMatches();
}
