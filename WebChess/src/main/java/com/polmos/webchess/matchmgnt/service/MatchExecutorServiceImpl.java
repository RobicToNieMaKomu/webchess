package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.matchmgnt.entity.Match;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * Asynchronous service which is responsible for match lifecycle handling.
 * Bean created in the spring-context.xml.
 *
 * @author Piotrek
 */
public class MatchExecutorServiceImpl implements MatchExecutorService {

    private static Logger logger = Logger.getLogger(MatchExecutorServiceImpl.class);
    // In seconds
    private final Integer REFRESH_TIME = 10;
    // In milis
    private final Integer ONE_SECOND = 1000;
    @Autowired
    private MatchService matchService;
    private Set<Integer> allRunningMatches;

    public MatchExecutorServiceImpl() {
        allRunningMatches = new HashSet<>();
    }

    @Override
    @Async
    public void start(final Integer matchId) {
        logger.debug("Strated async method start() for match:" + matchId);
        allRunningMatches.add(matchId);
        Match match = matchService.findMatchById(matchId);
        try {
            // Wait (max game time - 10 sec) and then start countdown
            Thread.sleep(match.getBplayerTime() * 1000 - REFRESH_TIME);
            // Check if game still exists
            if (allRunningMatches.contains(matchId)) {
                // Check players time and decide whether to start countdown
                match = matchService.findMatchById(matchId);
                int wplayerTime = match.getWplayerTime();
                int bplayerTime = match.getBplayerTime();
                if ((wplayerTime < 11 * ONE_SECOND) || (bplayerTime < 11 * ONE_SECOND)) {
                    // Start the final countdown ta da da da ta da da da
                    // Every each second push current status of the match to players
                    for (int i = 0; i < 10; i++) {
                        logger.debug("counting down..." + (10 - i));
                        // TBD push status to players
                        Thread.sleep(ONE_SECOND);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Something went wrong during countdown..." + e);
        }
    }

    @Override
    public void cleanMatches() {
    }
}
