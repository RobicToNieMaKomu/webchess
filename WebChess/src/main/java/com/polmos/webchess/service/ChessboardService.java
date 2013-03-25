package com.polmos.webchess.service;

import com.polmos.webchess.items.ChessboardPojo;

/**
 *
 * @author Piotrek
 */
public interface ChessboardService {
    ChessboardPojo createNewChessboard();
    ChessboardPojo getDesk();
    void setDesk(ChessboardPojo chessboard);
    String[] serializeChessboard(ChessboardPojo chessboard);
}
