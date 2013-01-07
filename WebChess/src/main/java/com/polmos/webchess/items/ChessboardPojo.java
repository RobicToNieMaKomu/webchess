package com.polmos.webchess.items;

import org.apache.log4j.Logger;

/** 
 *
 * @author Piotrek
 */

public class ChessboardPojo {
    
    private static Logger logger = Logger.getLogger(ChessboardPojo.class);
    private ChessmanPojo chessBoard[][];

    public ChessboardPojo() {
        logger.debug("ChessBoardPojo creation...");
        chessBoard = new ChessmanPojo[8][8];
    }
    
    public ChessmanPojo[][] getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessmanPojo[][] chessBoard) {
        this.chessBoard = chessBoard;
    }
}
