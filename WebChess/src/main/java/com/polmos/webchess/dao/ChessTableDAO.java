package com.polmos.webchess.dao;

import com.polmos.webchess.matchmgnt.entity.ChessTable;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface ChessTableDAO {

    void saveChessTable(ChessTable chessTable);

    ChessTable findMatchById(Integer id);

    List<ChessTable> findAllChessTables();

    void removeChessTable(ChessTable chessTable);
}
