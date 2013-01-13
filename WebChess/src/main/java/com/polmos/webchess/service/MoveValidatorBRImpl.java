package com.polmos.webchess.service;

import com.polmos.webchess.enums.ChessmanEnum;
import com.polmos.webchess.enums.ChessmanEnum;
import com.polmos.webchess.enums.ColorsEnum;
import com.polmos.webchess.enums.ColumnEnum;
import com.polmos.webchess.enums.ExceptionsEnum;
import com.polmos.webchess.enums.RowEnum;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessboardPojo;
import com.polmos.webchess.items.ChessmanPojo;
import com.polmos.webchess.items.ChessmanPojo;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 *
 * @author Piotrek
 */
@Service(value = "moveValidatorBR")
public class MoveValidatorBRImpl implements MoveValidatorBR {

    @Resource(name = "chessboardService")
    private ChessboardService chessboardService;

    /**
     * 
     * @param color of player who may be checked
     * @return is color checked by opponent
     */
    @Override
    public Boolean isChecked(ColorsEnum color, ChessmanPojo[][] chessboard) {
        Boolean isChecked = false;
        switch (color) {
            case WHITE:
                // First gather information about existing chessmen
                Map<ChessmanEnum, List<ChessmanPojo>> existingWhiteFigs = retriveExistingChessmen(color, chessboard);
                Map<ChessmanEnum, List<ChessmanPojo>> existingBlackFigs = retriveExistingChessmen(color, chessboard);
                for (ChessmanEnum item : existingWhiteFigs.keySet()) {
                    switch (item) {
                        case BISHOP:
                            break;
                        case KING:
                            break;
                        case KNIGHT:
                            break;
                        case PAWN:
                            break;
                        case QUEEN:
                            break;
                        case ROOK:
                            break;
                    }
                }
                // Needs to be checked if any chessman can move there
                // pawns
                // bishops
                // knights  
                // rooks
                // queen
                // king
                break;
            case BLACK:
                break;
        }
        return false;
    }

    @Override
    public Boolean isOnDiagonal(Integer startingColumn, Integer endColumn, Integer startingRow,
            Integer endRow) throws WebChessException {
        Boolean result = false;
        // We have 4 cases to check
        if (endColumn - startingColumn == endRow - startingRow
                || endColumn - startingColumn == startingRow - endRow
                || startingColumn - endColumn == startingRow - endRow
                || startingColumn - endColumn == endRow - startingRow) {
            result = true;
        }
        return result;
    }

    @Override
    public Boolean isOnLine(ColumnEnum startingColumn, ColumnEnum endColumn, RowEnum startingRow,
            RowEnum endRow) throws WebChessException {
        Boolean result = false;
        Integer startCol = startingColumn.getColumnPosition();
        Integer endCol = endColumn.getColumnPosition();
        Integer startR = startingRow.getRowNumber();
        Integer endR = endRow.getRowNumber();
        // We have 2 cases to check
        if (endCol - startCol == 0
                || startR - endR == 0) {
            result = true;
        }
        return result;
    }

    @Override
    public void rangeValidator(Integer col, Integer row, Integer endCol, Integer endRow) throws WebChessException {
        WebChessException ex = new WebChessException("Column or row is out of range");
        ex.setExceptionType(ExceptionsEnum.OUT_OF_RANGE);
        if (row < 0 || col < 0 || row > 7 || col > 7 || endCol < 0 || endRow < 0 || endCol > 7 || endRow > 7) {
            throw ex;
        } else if (col == endCol && row == endRow) {
            throw ex;
        }
    }

    /**
     * 
     * @param color of chessman who is about to move
     * @param column
     * @param row
     * @return true if chessman can't move there or false otherwise
     */
    @Override
    public Boolean isThisFieldOccupied(ColorsEnum color, ColumnEnum column, RowEnum row) {
        Boolean isThisOccupied = false;
        ChessboardPojo chessboardPojo = chessboardService.getDesk();
        ChessmanPojo[][] chessboard = chessboardPojo.getChessBoard();
        ChessmanPojo chessman = chessboard[column.getColumnPosition()][row.getRowNumber()];
        if (!isThisFieldNull(column, row) && chessman.getColor() == color) {
            isThisOccupied = true;
        }
        return isThisOccupied;
    }

    /**
     * @return true there is no chessman on this field
     */
    private Boolean isThisFieldNull(ColumnEnum column, RowEnum row) {
        Boolean isThisFieldNull = true;
        ChessboardPojo chessboardPojo = chessboardService.getDesk();
        ChessmanPojo[][] chessboard = chessboardPojo.getChessBoard();
        ChessmanPojo chessman = chessboard[column.getColumnPosition()][row.getRowNumber()];
        if (chessman != null) {
            isThisFieldNull = false;
        }
        return isThisFieldNull;
    }

    @Override
    public Boolean validateBishopMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException {
        // TODO - check if this implementation verifies a last bishop field/move is correct (ie there are no other figs.)
        Boolean isThisMovePossible = true;
        Boolean isDiagonal = false;
        isDiagonal = isOnDiagonal(chessman.getColumnPosition(), chessman.getRowPosition(), col, row);
        // If this isnt diagonal move then return false
        if (!isDiagonal) {
            return !isThisMovePossible;
        }
        // Starting positions
        Integer startRow = chessman.getRowPosition();
        Integer startColumn = chessman.getColumnPosition();
        // Prevent skipping other chessmen
        Boolean isFieldNull = true;
        Integer rowDiff = row - startRow;
        Integer columnDiff = col - startColumn;
        Integer moves = Math.abs(rowDiff);
        for (int i = 0; i < moves - 1; i++) {
            if (rowDiff > 0) {
                if (columnDiff > 0) {
                    // Right and Up
                    isFieldNull = isThisFieldNull(ColumnEnum.convertColumnPosition(startColumn + i), RowEnum.convertRowPosition(startRow + i));
                } else {
                    // Left and Up
                    isFieldNull = isThisFieldNull(ColumnEnum.convertColumnPosition(startColumn - i), RowEnum.convertRowPosition(startRow + i));
                }
            } else {
                if (columnDiff > 0) {
                    // Right and Down
                    isFieldNull = isThisFieldNull(ColumnEnum.convertColumnPosition(startColumn + i), RowEnum.convertRowPosition(startRow - i));
                } else {
                    // Left and Down
                    isFieldNull = isThisFieldNull(ColumnEnum.convertColumnPosition(startColumn - i), RowEnum.convertRowPosition(startRow - i));
                }
            }
            if (!isFieldNull) {
                isThisMovePossible = false;
                break;
            }
        }
        return isThisMovePossible;
    }

    @Override
    public Boolean validateKnightMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException {
        Boolean isThisMovePossible = false;
        Integer columnPosition = chessman.getColumnPosition();
        Integer rowPosition = chessman.getRowPosition();
        // There are 8 possibilities
        if (col - columnPosition == 2 || columnPosition - col == 2) {
            if (row - rowPosition == 1 || rowPosition - row == 1) {
                isThisMovePossible = true;
            }
        } else if (col - columnPosition == 1 || columnPosition - col == 1) {
            if (row - rowPosition == 2 || rowPosition - row == 2) {
                isThisMovePossible = true;
            }
        }
        return isThisMovePossible;
    }

    @Override
    public Boolean validateQueenMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException {
        Boolean isThisMovePossible = false;
        Integer startCol = chessman.getColumnPosition();
        Integer startRow = chessman.getRowPosition();
        Boolean isOnDiagonal = isOnDiagonal(startCol, startRow, col, row);
        Boolean isOnLine = isOnLine(ColumnEnum.convertColumnPosition(startCol), ColumnEnum.convertColumnPosition(col),
                RowEnum.convertRowPosition(startRow), RowEnum.convertRowPosition(row));
        // In fact there are only 2 possibilities here - queen moves diagonal or linear
        if (isOnDiagonal && validateBishopMove(chessman, col, row)) {
            isThisMovePossible = true;
        } else if (isOnLine && validateRookMove(chessman, col, row)) {
            isThisMovePossible = true;
        }
        return isThisMovePossible;
    }

    @Override
    public Boolean validateKingMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException {
        Boolean isThisMovePossible = false;
        // This should be queen move with shift equals to 1
        Integer colShift = Math.abs(chessman.getColumnPosition() - col);
        Integer rowShift = Math.abs(chessman.getRowPosition() - row);
        if (colShift <= 1 && rowShift <= 1) {
            isThisMovePossible = validateQueenMove(chessman, col, row);
        }
        return isThisMovePossible;
    }

    @Override
    public Boolean validateRookMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException {
        Boolean isThisMovePossible = true;
        // First of all verify on line req
        Integer startingCol = chessman.getColumnPosition();
        Integer startingRow = chessman.getRowPosition();
        ColumnEnum startCol = ColumnEnum.convertColumnPosition(startingCol);
        RowEnum startRow = RowEnum.convertRowPosition(startingRow);
        ColumnEnum endCol = ColumnEnum.convertColumnPosition(col);
        RowEnum endRow = RowEnum.convertRowPosition(row);
        if (!isOnLine(startCol, endCol, startRow, endRow)) {
            return !isThisMovePossible;
        }
        // Check if there are no obstacles on the chessman path
        Integer verDiff = startingRow - row;
        Integer horDiff = startingCol - col;
        Integer rowDiff = Math.abs(verDiff);
        Integer colDiff = Math.abs(horDiff);
        Integer iterations = 0;
        if (rowDiff == 0) {
            iterations = colDiff;
        } else if (colDiff == 0) {
            iterations = rowDiff;
        }
        Boolean isFieldNull = true;
        for (int i = 0; i < iterations - 1; i++) {
            if (rowDiff > 0 && colDiff == 0) {
                if (verDiff > 0) {
                    // Left
                    isFieldNull = isThisFieldNull(ColumnEnum.convertColumnPosition(startingCol - i), endRow);
                } else {
                    // Right
                    isFieldNull = isThisFieldNull(ColumnEnum.convertColumnPosition(startingCol - i), endRow);
                }
            } else if (rowDiff == 0 && colDiff > 0) {
                if (horDiff > 0) {
                    // Top
                    isFieldNull = isThisFieldNull(ColumnEnum.convertColumnPosition(startingCol - i), endRow);
                } else {
                    // Bottom
                    isFieldNull = isThisFieldNull(ColumnEnum.convertColumnPosition(startingCol - i), endRow);
                }
            }
            // Finally verify path
            if (!isFieldNull) {
                isThisMovePossible = false;
                break;
            }
        }
        return isThisMovePossible;
    }

    @Override
    public Boolean validateWhitePawnMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException {
        Boolean isThisMovePossible = false;

        return isThisMovePossible;
    }

    @Override
    public Boolean validateBlackPawnMove(ChessmanPojo chessman, Integer col, Integer row) throws WebChessException {
        Boolean isThisMovePossible = false;

        return isThisMovePossible;
    }

    private Map<ChessmanEnum, List<ChessmanPojo>> retriveExistingChessmen(ColorsEnum color, ChessmanPojo[][] chessboard) {
        Map<ChessmanEnum, List<ChessmanPojo>> result = new EnumMap<ChessmanEnum, List<ChessmanPojo>>(ChessmanEnum.class);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessmanPojo chessman = chessboard[i][j];
                if (chessman != null && color.equals(chessman.getColor())) {
                    ChessmanEnum chessmanType = chessman.getChessmanType();
                    List<ChessmanPojo> chessmen = result.get(chessmanType);
                    if (chessmen == null) {
                        chessmen = new ArrayList<ChessmanPojo>();
                    }
                    chessmen.add(chessman);
                    result.put(chessmanType, chessmen);
                }
            }
        }
        return result;
    }
}
