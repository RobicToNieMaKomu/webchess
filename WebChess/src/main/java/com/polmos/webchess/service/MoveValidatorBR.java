package com.polmos.webchess.service;

import com.polmos.webchess.enums.ColorsEnum;
import com.polmos.webchess.enums.ColumnEnum;
import com.polmos.webchess.enums.RowEnum;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessmanPojo;

/**
 *
 * @author Piotrek
 */
public interface MoveValidatorBR {

    Boolean isChecked(ColorsEnum color, ChessmanPojo[][] chessboard);

    Boolean isOnDiagonal(Integer startingColumn, Integer endColumn, Integer startingRow,
            Integer endRow) throws WebChessException;

    Boolean isOnLine(ColumnEnum startingColumn, ColumnEnum endColumn, RowEnum startingRow,
            RowEnum endRow) throws WebChessException;

    Boolean isThisFieldOccupied(ColorsEnum color, ColumnEnum column, RowEnum row);

    void rangeValidator(Integer col, Integer row, Integer endCol, Integer endRow) throws WebChessException;

    Boolean validateBishopMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException;

    Boolean validateKnightMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException;

    Boolean validateQueenMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException;

    Boolean validateKingMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException;

    Boolean validateRookMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException;

    Boolean validateWhitePawnMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException;

    Boolean validateBlackPawnMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException;
}
