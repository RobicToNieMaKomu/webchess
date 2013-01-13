package com.polmos.webchess.service;

import com.polmos.webchess.enums.ColorsEnum;
import com.polmos.webchess.enums.ColumnEnum;
import com.polmos.webchess.enums.RowEnum;
import com.polmos.webchess.items.ChessboardPojo;
import com.polmos.webchess.items.ChessmanPojo;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Piotrek
 */
@Service(value = "chessboardService")
public class ChessboardServiceImpl implements ChessboardService {

    @Resource(name = "chessmanFactory")
    private ChessmanFactory chessmanFactory;
    private ChessboardPojo chessboard;
    private static Logger logger = Logger.getLogger(ChessboardServiceImpl.class);
    private final Integer PAWN_COUNT = 8;
    public ChessboardServiceImpl() {
        logger.debug("ChessboardServiceImpl is creating...");
    }

    @Override
    public ChessboardPojo createNewChessboard() {
        ChessboardPojo desk = new ChessboardPojo();
        logger.debug("Creating new chessboard");
        initialSetUp(desk);
        return desk;
    }

    private void initialSetUp(ChessboardPojo chessboard) {
        logger.debug("Creating white chessmans");
        ChessmanPojo[][] newChessboard = chessboard.getChessBoard();
        // White chessmen
        ChessmanPojo aRook = chessmanFactory.createRook(ColorsEnum.WHITE, ColumnEnum.A.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo bRook = chessmanFactory.createRook(ColorsEnum.WHITE, ColumnEnum.H.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo aKnight = chessmanFactory.createKnight(ColorsEnum.WHITE, ColumnEnum.B.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo bKnight = chessmanFactory.createKnight(ColorsEnum.WHITE, ColumnEnum.G.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo aBishop = chessmanFactory.createBishop(ColorsEnum.WHITE, ColumnEnum.C.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo bBishop = chessmanFactory.createBishop(ColorsEnum.WHITE, ColumnEnum.F.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo queen = chessmanFactory.createQueen(ColorsEnum.WHITE, ColumnEnum.D.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo king = chessmanFactory.createKing(ColorsEnum.WHITE, ColumnEnum.E.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        // Set up those chessmen at the chessboard
        newChessboard[ColumnEnum.A.getColumnPosition()][RowEnum.ONE.getRowNumber()] = aRook;
        newChessboard[ColumnEnum.B.getColumnPosition()][RowEnum.ONE.getRowNumber()] = aKnight;
        newChessboard[ColumnEnum.C.getColumnPosition()][RowEnum.ONE.getRowNumber()] = aBishop;
        newChessboard[ColumnEnum.D.getColumnPosition()][RowEnum.ONE.getRowNumber()] = queen;
        newChessboard[ColumnEnum.E.getColumnPosition()][RowEnum.ONE.getRowNumber()] = king;
        newChessboard[ColumnEnum.F.getColumnPosition()][RowEnum.ONE.getRowNumber()] = bBishop;
        newChessboard[ColumnEnum.G.getColumnPosition()][RowEnum.ONE.getRowNumber()] = bKnight;
        newChessboard[ColumnEnum.H.getColumnPosition()][RowEnum.ONE.getRowNumber()] = bRook;
        // White pawns
        for (int i = 0; i < PAWN_COUNT; i++) {
            ChessmanPojo pawn = chessmanFactory.createPawn(ColorsEnum.WHITE, i, RowEnum.TWO.getRowNumber());
            newChessboard[i][RowEnum.TWO.getRowNumber()] = pawn;
        }
        // Black chessmen
        ChessmanPojo acRook = chessmanFactory.createRook(ColorsEnum.BLACK, ColumnEnum.A.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo bcRook = chessmanFactory.createRook(ColorsEnum.BLACK, ColumnEnum.H.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo acKnight = chessmanFactory.createKnight(ColorsEnum.BLACK, ColumnEnum.B.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo bcKnight = chessmanFactory.createKnight(ColorsEnum.BLACK, ColumnEnum.G.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo acBishop = chessmanFactory.createBishop(ColorsEnum.BLACK, ColumnEnum.C.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo bcBishop = chessmanFactory.createBishop(ColorsEnum.BLACK, ColumnEnum.F.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo cqueen = chessmanFactory.createQueen(ColorsEnum.BLACK, ColumnEnum.D.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo cking = chessmanFactory.createKing(ColorsEnum.BLACK, ColumnEnum.E.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        // Set up those chessmen at the chessboard
        newChessboard[ColumnEnum.A.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = acRook;
        newChessboard[ColumnEnum.B.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = acKnight;
        newChessboard[ColumnEnum.C.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = acBishop;
        newChessboard[ColumnEnum.D.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = cqueen;
        newChessboard[ColumnEnum.E.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = cking;
        newChessboard[ColumnEnum.F.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = bcBishop;
        newChessboard[ColumnEnum.G.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = bcKnight;
        newChessboard[ColumnEnum.H.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = bcRook;
        // Black pawns
        for (int i = 0; i < PAWN_COUNT; i++) {
            ChessmanPojo cpawn = chessmanFactory.createPawn(ColorsEnum.BLACK, i, RowEnum.SEVEN.getRowNumber());
            newChessboard[i][RowEnum.SEVEN.getRowNumber()] = cpawn;
        }
    }

    @Override
    public ChessboardPojo getDesk() {
        return this.chessboard;
    }

    @Override
    public void setDesk(ChessboardPojo chessboard) {
        this.chessboard = chessboard;
    }
}
