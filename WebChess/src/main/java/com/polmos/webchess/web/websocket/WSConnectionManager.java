package com.polmos.webchess.web.websocket;

import java.util.Set;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface WSConnectionManager {

    void createNewWSBinding(ClientMessageInbound wsClientConnection);
    /**
     * Save handler to new opened ws connection
     */
    void addNewWSConnection(ClientMessageInbound wsClientConnection, Integer chessTableId);

    /**
     * Remove inactive ws connection
     */
    void removeWSConnection(ClientMessageInbound wsClientConnection, Set<Integer> chessTableIDs);

    /**
     * Method returns (one or many) ids of tables currently being visited by client (User)
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
    Set<ClientMessageInbound> findWSConnectionsByChessTable(Integer chessTableId);

    /**
     * Method finds client by name.
     *
     * @param userName - client's name contained in http request
     * @return ws client inbound or null if user with given name does not exist
     */
    ClientMessageInbound findWSConnectionByUserName(String userName);

    /**
     * Method used to broadcasting messages to all clients connected to chess table
     * with given id.
     * 
     * @param message to be broadcasted
     * @param chessTableId  
     */
    void broadcastToClientsInChessRoom(String message, Integer chessTableId);

    /**
     * Method used to broadcasting messages to all connected clients in all rooms
     * 
     * @param message 
     */
    void broadcastToAllClients(String message);
}
