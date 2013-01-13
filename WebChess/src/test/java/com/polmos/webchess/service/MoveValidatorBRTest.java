/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;
import com.polmos.webchess.enums.ColumnEnum;
import com.polmos.webchess.enums.RowEnum;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessmanPojo;
import org.apache.log4j.Logger;

import static org.hamcrest.CoreMatchers.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Piotrek
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:service-context.xml"})
public class MoveValidatorBRTest {

    private static Logger logger = Logger.getLogger(MoveValidatorBRTest.class);
    private static ChessmanPojo chessman;
    private static final ColumnEnum STARTING_COLUMN = ColumnEnum.D;
    private static final RowEnum STARTING_ROW = RowEnum.FOUR;
    private static ColumnEnum endColumn;
    private static RowEnum endRow;
    @Resource(name = "moveValidatorBR")
    private MoveValidatorBR moveValidatorBR;

    @BeforeClass
    public static void setUpClass() {
        chessman = new ChessmanPojo();
        chessman.setColumnPosition(STARTING_COLUMN.getColumnPosition());
        chessman.setRowPosition(STARTING_ROW.getRowNumber());
    }

    @Test
    public void isLocationOnDiagonaleTest() {
        try {
            logger.debug("isLocationOnDiagonaleTest");
            Boolean isOnDiagonale = false;
            endColumn = ColumnEnum.A;
            endRow = RowEnum.SEVEN;
            isOnDiagonale = moveValidatorBR.isOnDiagonal(STARTING_COLUMN.getColumnPosition(), endColumn.getColumnPosition(),
                    STARTING_ROW.getRowNumber(), endRow.getRowNumber());
            assertThat(isOnDiagonale, is(true));
            endColumn = ColumnEnum.H;
            endRow = RowEnum.EIGHT;
            isOnDiagonale = moveValidatorBR.isOnDiagonal(STARTING_COLUMN.getColumnPosition(), endColumn.getColumnPosition(),
                    STARTING_ROW.getRowNumber(), endRow.getRowNumber());
            assertThat(isOnDiagonale, is(true));
            endColumn = ColumnEnum.E;
            endRow = RowEnum.THREE;
            isOnDiagonale = moveValidatorBR.isOnDiagonal(STARTING_COLUMN.getColumnPosition(), endColumn.getColumnPosition(),
                    STARTING_ROW.getRowNumber(), endRow.getRowNumber());
            assertThat(isOnDiagonale, is(true));
            endColumn = ColumnEnum.B;
            endRow = RowEnum.SIX;
            isOnDiagonale = moveValidatorBR.isOnDiagonal(STARTING_COLUMN.getColumnPosition(), endColumn.getColumnPosition(),
                    STARTING_ROW.getRowNumber(), endRow.getRowNumber());
            assertThat(isOnDiagonale, is(true));
            endColumn = ColumnEnum.A;
            endRow = RowEnum.EIGHT;
            isOnDiagonale = moveValidatorBR.isOnDiagonal(STARTING_COLUMN.getColumnPosition(), endColumn.getColumnPosition(),
                    STARTING_ROW.getRowNumber(), endRow.getRowNumber());
            assertThat(isOnDiagonale, is(false));
            endColumn = ColumnEnum.B;
            endRow = RowEnum.EIGHT;
            isOnDiagonale = moveValidatorBR.isOnDiagonal(STARTING_COLUMN.getColumnPosition(), endColumn.getColumnPosition(),
                    STARTING_ROW.getRowNumber(), endRow.getRowNumber());
            assertThat(isOnDiagonale, is(false));
        } catch (WebChessException ex) {
            logger.debug(ex.getMessage());
        }
    }

    @Test
    public void isLocationOnLineTest() {
        try {
            logger.debug("isLocationOnLineTest");
            Boolean isOnLine = false;
            endColumn = ColumnEnum.D;
            endRow = RowEnum.SEVEN;
            isOnLine = moveValidatorBR.isOnLine(STARTING_COLUMN, endColumn, STARTING_ROW, endRow);
            assertThat(isOnLine, is(true));
            endColumn = ColumnEnum.E;
            endRow = RowEnum.FOUR;
            isOnLine = moveValidatorBR.isOnLine(STARTING_COLUMN, endColumn, STARTING_ROW, endRow);
            assertThat(isOnLine, is(true));
            endColumn = ColumnEnum.F;
            endRow = RowEnum.FOUR;
            isOnLine = moveValidatorBR.isOnLine(STARTING_COLUMN, endColumn, STARTING_ROW, endRow);
            assertThat(isOnLine, is(true));
            endColumn = ColumnEnum.B;
            endRow = RowEnum.SIX;
            isOnLine = moveValidatorBR.isOnLine(STARTING_COLUMN, endColumn, STARTING_ROW, endRow);
            assertThat(isOnLine, is(false));
        } catch (WebChessException ex) {
            logger.debug(ex.getMessage());
        }
    }

    @Test
    public void moveCircumstancesTest() {
        try {
            logger.debug("moveCircumstancesTest");
            Integer endCol = 1;
            Integer endRow = 3;
            moveValidatorBR.rangeValidator(STARTING_COLUMN.getColumnPosition(), STARTING_ROW.getRowNumber(),
                    endCol, endRow);
        } catch (WebChessException ex) {
            logger.debug(ex.getMessage());
        }
    }

    @Test(expected = WebChessException.class)
    public void invalidLocationTest1() throws WebChessException {
        logger.debug("invalidLocationTest1");
        Integer endCol = -1;
        Integer endRow = 3;
        moveValidatorBR.rangeValidator(STARTING_COLUMN.getColumnPosition(), STARTING_ROW.getRowNumber(),
                endCol, endRow);
    }

    @Test(expected = WebChessException.class)
    public void invalidLocationTest2() throws WebChessException {
        logger.debug("invalidLocationTest2");
        Integer endCol = 1;
        Integer endRow = -3;
        moveValidatorBR.rangeValidator(STARTING_COLUMN.getColumnPosition(), STARTING_ROW.getRowNumber(),
                endCol, endRow);
    }

    @Test(expected = WebChessException.class)
    public void invalidLocationTest3() throws WebChessException {
        logger.debug("invalidLocationTest3");
        Integer endCol = 7;
        Integer endRow = 8;
        moveValidatorBR.rangeValidator(STARTING_COLUMN.getColumnPosition(), STARTING_ROW.getRowNumber(),
                endCol, endRow);
    }

    @Test(expected = WebChessException.class)
    public void invalidLocationTest4() throws WebChessException {
        logger.debug("invalidLocationTest4");
        Integer endCol = -1;
        Integer endRow = 10;
        moveValidatorBR.rangeValidator(STARTING_COLUMN.getColumnPosition(), STARTING_ROW.getRowNumber(),
                endCol, endRow);
    }
}
