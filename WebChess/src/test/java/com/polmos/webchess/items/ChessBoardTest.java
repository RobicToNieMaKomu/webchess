/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.items;

import com.polmos.webchess.exceptions.WebChessException;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.polmos.webchess.service.ChessboardService;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Piotrek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:service-context.xml"})
public class ChessBoardTest {

    @Resource(name = "chessboardService")
    private ChessboardService chessboardService;
    private ChessboardPojo chessboard;
    private static final Logger logger = Logger.getLogger(ChessBoardTest.class);
    private final Integer CHESSMEN_COUNT = 32;
    private final Integer FIELDS_COUNT = 64;
    private final Integer EXPECTED_ROW_COUNT = 8;
    
    
    @Before
    public void setUp() {
        logger.debug("Setting up ChessboardTest...");
        chessboard = chessboardService.createNewChessboard();
    }

    @Test
    public void initialSetUpValidationTest() {
        logger.debug("Initial Set Up Validation test");
        ChessmanPojo [][] desk = chessboard.getChessBoard();
        int chessmen_count = 0;
        for(int i = 0; i<8; i++) {
            for(int j = 0; j<8; j++) {
                if(desk[i][j] instanceof ChessmanPojo) {
                    chessmen_count++;
                }
            }
        }
        assertThat(chessmen_count, is(CHESSMEN_COUNT));
    }
    
    @Test
    public void serializeChessboardTest() {
        String[] serializedChessboard = chessboardService.transformChessboardToArray(chessboard);
        for (int i = 0; i < serializedChessboard.length; i++) {
            logger.debug("field i:"+ i + "="+serializedChessboard[i]);
        }
        assertThat(serializedChessboard.length, is(FIELDS_COUNT));
    }
    
    @Test
    public void transformChessboardTableToMapTest() throws WebChessException {
        logger.debug("transformChessboardTableToMapTest started...");
        Map<String, String> resultMap = chessboardService.transformChessboardTableToMap(chessboard);
        logger.debug(resultMap);
        assertThat(resultMap.entrySet().size(), is(FIELDS_COUNT));
    }
}
