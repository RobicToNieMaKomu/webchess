package com.polmos.webchess.web.websocket;

import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface WSConnectionManager {
    
    /**
     * Save handler to new opened ws connection
     */
    void addNewWSConnection(ClientMessageInbound wsClientConnection, Integer chessTableId);
    /**
     * Remove unactive ws connection
     */
    void removeWSConnection(ClientMessageInbound wsClientConnection);
    /**
     * Method returns one or many table ids currently visiting by client (User) 
     * 
     * @param clientWS
     * @return table ids
     */
    Set<Integer> getChessTableIDs(ClientMessageInbound clientWS);

    /**
     * Method finds all clients connected to chess table (room) with given id. 
     * Use this for finding candidates for broadcast.
     * 
     * @param chessTableId
     * @return clients sitting in specified room
     */
    Set<ClientMessageInbound> findWSConnectionsToChessTable(Integer chessTableId);
}
