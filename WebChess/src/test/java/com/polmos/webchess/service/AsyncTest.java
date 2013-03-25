package com.polmos.webchess.service;

import com.polmos.webchess.matchmgnt.entity.ChessTable;
import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.service.MatchExecutorService;
import com.polmos.webchess.matchmgnt.service.MatchService;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.staticmock.MockStaticEntityMethods;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Piotrek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:service-context.xml"})
public class AsyncTest {
    
    private static Logger logger = Logger.getLogger(AsyncTest.class);
    private final Integer EXPECTED_NUMBER_OF_MATCHES = 100;
    private final Integer EXPECTED_TIME = 13;
    
    
    @Autowired
    private MatchExecutorService matchExecutorService;
    
    @Test
    public void testAsyncMethod() {
        logger.debug("Testing MatchService: async method");
        Set<Match> matchesContainer = createMatches(EXPECTED_NUMBER_OF_MATCHES);
        for (Match match : matchesContainer) {
            matchExecutorService.start(match.getMatchId());
        }
        
    }
    
    public Set<Match> createMatches(Integer matchCount) {
        Set<Match> result = new HashSet<>();
        for (int i = 0; i < matchCount; i++) {
            Match match = new Match();
            ChessTable chessTable = new ChessTable();
            chessTable.setTableId(i);
            match.setTableid(chessTable);
            match.setMatchId(i);
            match.setWplayerTime(i);
            match.setBplayerTime(i);
            result.add(match);
        }
        return result;
    }
}
