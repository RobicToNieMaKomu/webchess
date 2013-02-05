package com.polmos.webchess.dao;

import com.polmos.webchess.matchmgnt.entity.ChessTable;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface ChessTableDAO {

    Integer createChessTable(ChessTable chessTable);

    ChessTable findChessTableById(Integer id);

    List<ChessTable> findAllChessTables();

    void removeChessTable(ChessTable chessTable);

    void updateChessTable(ChessTable chessTable);
    
    Long getChessTablesCount();
}
