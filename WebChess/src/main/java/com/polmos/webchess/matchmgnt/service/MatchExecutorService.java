package com.polmos.webchess.matchmgnt.service;

/**
 *
 * @author Piotrek
 */
public interface MatchExecutorService {
    
    void start(Integer matchId);
    void cleanMatches();
}
