package com.polmos.webchess.service;

import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessboardPojo;
import java.util.Map;

/**
 *
 * @author Piotrek
 */
public interface ChessboardService {
    ChessboardPojo createNewChessboard();
    ChessboardPojo getDesk();
    void setDesk(ChessboardPojo chessboard);
    String[] serializeChessboard(ChessboardPojo chessboard);
    Map<String, String> transformChessboardTableToMap(ChessboardPojo chessboard) throws WebChessException;
}
