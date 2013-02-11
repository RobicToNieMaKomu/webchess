package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.dao.ChessTableDAO;
import com.polmos.webchess.matchmgnt.entity.ChessTable;
import com.polmos.webchess.matchmgnt.entity.User;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author RobicToNieMaKomu
 */
@Service("chessTableService")
public class ChessTableServiceImpl implements ChessTableService {

    private final Integer DEFAULT_GAME_TIME = 900; // 15 min
    private final Long MAX_TABLES_COUNT = 10l;
    private final Integer TABLE_CREATION_REJECTED = -1;
    
    @Autowired
    private ChessTableDAO chessTableDAO;

    @Override
    public List<ChessTable> getAllChessTables() {
        return chessTableDAO.findAllChessTables();
    }

    @Override
    public Integer createNewChessTable(Date lastVisitTimestamp, User user) {
        Integer result = TABLE_CREATION_REJECTED;
        ChessTable chessTable = new ChessTable();
        chessTable.setLastVisitTimestamp(lastVisitTimestamp);
        chessTable.setGameTime(DEFAULT_GAME_TIME);
        chessTable.setWplayer(user);
        // Currently, there is a limit of 100 chess tables (at one moment).
        Long chessTablesCount = chessTableDAO.getChessTablesCount();
        if (chessTablesCount < MAX_TABLES_COUNT) {
            result = chessTableDAO.createChessTable(chessTable);
        } 
        return result;
    }

    @Transactional
    @Override
    public void updateStateOfChessTable(Integer tableId, User wPlayer, User bPlayer, Integer gameTime, Date lastVisitTimestamp) {
        ChessTable chessTable = chessTableDAO.findChessTableById(tableId);
        // add players and set game duration for this table
        chessTable.setBplayer(bPlayer);
        chessTable.setWplayer(wPlayer);
        chessTable.setGameTime(gameTime);
        chessTable.setLastVisitTimestamp(lastVisitTimestamp);
        // update state of this table
        chessTableDAO.updateChessTable(chessTable);
    }

    @Override
    public ChessTable findChessTable(Integer tableId) {
        ChessTable result = chessTableDAO.findChessTableById(tableId);
        return result;
    }
}
