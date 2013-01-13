package com.polmos.webchess.service;

import com.polmos.webchess.enums.ChessmanEnum;
import com.polmos.webchess.enums.ColorsEnum;
import com.polmos.webchess.items.ChessmanPojo;
import org.springframework.stereotype.Service;

/**
 *
 * @author Piotrek
 */
@Service(value = "chessmanFactory")
public class ChessmanFactoryImpl implements ChessmanFactory {

    @Override
    public ChessmanPojo createPawn(ColorsEnum color, Integer columnPosition, Integer rowPosition) {
        ChessmanPojo pawn = new ChessmanPojo();
        setParameters(pawn, rowPosition, columnPosition, color);
        pawn.setChessmanType(ChessmanEnum.PAWN);

        return pawn;
    }

    @Override
    public ChessmanPojo createKnight(ColorsEnum color, Integer columnPosition, Integer rowPosition) {
        ChessmanPojo knight = new ChessmanPojo();
        setParameters(knight, rowPosition, columnPosition, color);
        knight.setChessmanType(ChessmanEnum.KNIGHT);

        return knight;
    }

    @Override
    public ChessmanPojo createBishop(ColorsEnum color, Integer columnPosition, Integer rowPosition) {
        ChessmanPojo bishop = new ChessmanPojo();
        setParameters(bishop, rowPosition, columnPosition, color);
        bishop.setChessmanType(ChessmanEnum.BISHOP);

        return bishop;
    }

    @Override
    public ChessmanPojo createRook(ColorsEnum color, Integer columnPosition, Integer rowPosition) {
        ChessmanPojo rook = new ChessmanPojo();
        setParameters(rook, rowPosition, columnPosition, color);
        rook.setChessmanType(ChessmanEnum.ROOK);

        return rook;
    }

    @Override
    public ChessmanPojo createQueen(ColorsEnum color, Integer columnPosition, Integer rowPosition) {
        ChessmanPojo queen = new ChessmanPojo();
        setParameters(queen, rowPosition, columnPosition, color);
        queen.setChessmanType(ChessmanEnum.QUEEN);

        return queen;
    }

    @Override
    public ChessmanPojo createKing(ColorsEnum color, Integer columnPosition, Integer rowPosition) {
        ChessmanPojo king = new ChessmanPojo();
        setParameters(king, rowPosition, columnPosition, color);
        king.setChessmanType(ChessmanEnum.KING);

        return king;
    }

    private void setParameters(ChessmanPojo chessman, Integer rowPosition, Integer columnPosition,
            ColorsEnum color) {
        chessman.setColumnPosition(columnPosition);
        chessman.setRowPosition(rowPosition);
        chessman.setColor(color);
    }
}
