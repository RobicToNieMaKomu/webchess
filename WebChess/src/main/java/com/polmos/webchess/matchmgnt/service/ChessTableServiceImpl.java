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

    private Integer DEFAULT_GAME_TIME = 900; // 15 min
    @Autowired
    private ChessTableDAO chessTableDAO;

    @Override
    public List<ChessTable> getAllChessTables() {
        return chessTableDAO.findAllChessTables();
    }

    @Override
    public Integer createNewChessTable(Date lastVisitTimestamp) {
        ChessTable chessTable = new ChessTable();
        chessTable.setLastVisitTimestamp(lastVisitTimestamp);
        chessTable.setGameTime(DEFAULT_GAME_TIME);
        Integer chessTableId = chessTableDAO.createChessTable(chessTable);
        return chessTableId;
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
}
