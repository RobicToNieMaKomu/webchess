package com.polmos.webchess.service;

import com.polmos.webchess.enums.ColorsEnum;
import com.polmos.webchess.enums.ColumnEnum;
import com.polmos.webchess.enums.ExceptionsEnum;
import com.polmos.webchess.enums.RowEnum;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessmanPojo;
import java.util.Calendar;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Piotrek
 */
@Service(value = "moveValidator")
public class MoveValidatorImpl implements MoveValidator {

    private static Logger logger = Logger.getLogger(MoveValidatorImpl.class);
    @Resource(name = "moveValidatorBR")
    private MoveValidatorBR moveValidatorBR;

    public MoveValidatorImpl() {
        logger.debug("MoveValidatorImpl "+Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public Boolean validateMove(ChessmanPojo chessman, Integer endRowPosition, Integer endColumnPosition) throws WebChessException {
        try {
            logger.debug("Validating chessman move: " + chessman.getChessmanType());
            logger.debug("From column position: " + chessman.getColumnPosition());
            logger.debug("From row position: " + chessman.getRowPosition());
            logger.debug("To row position: " + endRowPosition);
            logger.debug("To column position: " + endColumnPosition);
            Boolean isThisMovePossible = false;
            Boolean isThisMoveExecutesCheck = false;
            ColorsEnum chessmanColor = chessman.getColor();
            logger.debug("Validating positions...");
            moveValidatorBR.rangeValidator(chessman.getColumnPosition(), chessman.getRowPosition(), endColumnPosition,
                    endRowPosition);
            logger.debug("Validating check opportunities...");
            //isThisMoveExecutesCheck = moveValidatorBR.isChecked(chessmanColor);
            logger.debug("Validating field occupation...");
            Boolean isFieldOccupied = moveValidatorBR.isThisFieldOccupied(chessmanColor, ColumnEnum.convertColumnPosition(endColumnPosition),
                    RowEnum.convertRowPosition(endRowPosition));
            logger.debug("Validating move correctness...");
            switch (chessman.getChessmanType()) {
                case KING:
                    isThisMovePossible = moveValidatorBR.validateKingMove(chessman, endColumnPosition, endRowPosition);
                    break;
                case KNIGHT:
                    isThisMovePossible = moveValidatorBR.validateKnightMove(chessman, endColumnPosition, endRowPosition);
                    break;
                case BISHOP:
                    isThisMovePossible = moveValidatorBR.validateBishopMove(chessman, endColumnPosition, endRowPosition);
                    break;
                case QUEEN:
                    isThisMovePossible = moveValidatorBR.validateQueenMove(chessman, endColumnPosition, endRowPosition);
                    break;
                case PAWN:
                    switch (chessmanColor) {
                        case WHITE:
                            isThisMovePossible = moveValidatorBR.validateWhitePawnMove(chessman, endColumnPosition, endRowPosition);
                            break;
                        case BLACK:
                            isThisMovePossible = moveValidatorBR.validateBlackPawnMove(chessman, endColumnPosition, endRowPosition);
                            break;
                    }
                    break;
                case ROOK:
                    isThisMovePossible = moveValidatorBR.validateRookMove(chessman, endColumnPosition, endRowPosition);
                    break;
                default:
                    WebChessException ex = new WebChessException("Unknown chessman type");
                    ex.setExceptionType(ExceptionsEnum.GENERAL_BR_ERROR);
                    throw ex;
            }

            if (isFieldOccupied == true || isThisMoveExecutesCheck == true || isThisMovePossible == false) {
                logger.debug("Validation failed - "
                        + "field is occupied: " + isFieldOccupied
                        + "this move causes check: " + isThisMoveExecutesCheck
                        + "this move isnt possible: " + isThisMovePossible);
                return false;
            } else {
                logger.debug("Validation success - this move can be done");
                return true;
            }
        } catch (WebChessException exception) {
            logger.debug("Validation failed because an exception is thrown: " + exception.getMessage());
            throw exception;
        }
    }
}
