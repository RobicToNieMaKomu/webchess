/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.matchmgnt.entity.ChessTable;
import com.polmos.webchess.matchmgnt.entity.User;
import java.util.Date;
import java.util.List;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface ChessTableService {

    List<ChessTable> getAllChessTables();

    /**
     * Creates a default table (with a default game time) or returns null if the 
     * limit of chess tables is reached. 
     * This method is called whenever user requests new table.
     * 
     * @param lastVisitTimestamp
     * @param User user requesting new table
     * @return id of the created table or null if table was not created
     */
    Integer createNewChessTable(Date lastVisitTimestamp, User user);
    
    /**
     * This method is called when the game is being started. 
     * 
     * @param tableId id of the chess table
     * @param wPlayer user playing white
     * @param bPlayer user playing black
     * @param gameTime game duration
     * @param lastVisitTimestamp time of game's beggnining
     */
    void updateStateOfChessTable(Integer tableId, User wPlayer, User bPlayer, Integer gameTime, Date lastVisitTimestamp);
    
    /**
     * Returns chess table or null if table does not exist.
     * 
     * @param tableId
     * @return 
     */
    ChessTable findChessTable(Integer tableId);
}
