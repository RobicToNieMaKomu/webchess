/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.items;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.polmos.webchess.service.ChessboardService;
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
}