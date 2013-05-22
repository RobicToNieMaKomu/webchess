package com.polmos.webchess.web.websocket;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Service managing WS connections requested in ChatWebSocketServlet and opened
 * in each ClientMessageInbound.
 *
 * @author RobicToNieMaKomu
 */
@Service(value = "wSConnectionManager")
public class WSConnectionManagerImpl implements WSConnectionManager {

    private final static Logger logger = Logger.getLogger(WSConnectionManagerImpl.class);
    // Contains all active ws connections
    private final Set<ClientMessageInbound> wsConnections;
    // Auxillary maps containing wsconnections and chess tables
    private final Map<ClientMessageInbound, Set<Integer>> clientToRoomsMap;
    private final Map<Integer, Set<ClientMessageInbound>> chessTableIdToClientsMap;

    public WSConnectionManagerImpl() {
        // Initializng containers
        wsConnections = new CopyOnWriteArraySet<>();
        clientToRoomsMap = new HashMap<>();
        chessTableIdToClientsMap = new HashMap<>();
        logger.debug("WSConnectionManagerImpl created");
    }

    @Override
    public void addNewWSConnection(ClientMessageInbound wsClientConnection, Integer chessTableId) {
        wsConnections.add(wsClientConnection);
        // Add room to the clientToRooms map
        Set<Integer> chessRoomIds = clientToRoomsMap.get(wsClientConnection);
        if (chessRoomIds == null) {
            chessRoomIds = new HashSet<>();
        }
        chessRoomIds.add(chessTableId);
        clientToRoomsMap.put(wsClientConnection, chessRoomIds);
        // Add client to the roomToClients map
        Set<ClientMessageInbound> clients = chessTableIdToClientsMap.get(chessTableId);
        if (clients == null) {
            clients = new HashSet<>();
        }
        clients.add(wsClientConnection);
        chessTableIdToClientsMap.put(chessTableId, clients);
    }

    @Override
    public void removeWSConnection(ClientMessageInbound wsClientConnection, Set<Integer> chessTableIDs) {
        for (Integer tableId : chessTableIDs) {
            Set<ClientMessageInbound> allPlayersInRoom = chessTableIdToClientsMap.get(tableId);
            Iterator<ClientMessageInbound> iterator = allPlayersInRoom.iterator();
            while (iterator.hasNext()) {
                ClientMessageInbound wsClient = iterator.next();
                if (wsClient.equals(wsClientConnection)) {
                    iterator.remove();
                }
            }
        }
        clientToRoomsMap.remove(wsClientConnection);
        // TODO: check whether wsConnections is necessary...
        wsConnections.remove(wsClientConnection);
    }

    @Override
    public Set<Integer> getChessTableIDs(ClientMessageInbound clientWS) {
        Set<Integer> result = clientToRoomsMap.get(clientWS);
        if (result == null) {
            result = new HashSet<>();
        }
        return result;
    }

    @Override
    public Set<ClientMessageInbound> findWSConnectionsByChessTable(Integer chessTableId) {
        Set<ClientMessageInbound> result = chessTableIdToClientsMap.get(chessTableId);
        if (result == null) {
            result = new HashSet<>();
        }
        return result;
    }

    @Override
    public ClientMessageInbound findWSConnectionByUserName(String userName) {
        ClientMessageInbound result = null;
        if (userName != null) {
            for (ClientMessageInbound wsConnection : wsConnections) {
                if (userName.equals(wsConnection.getUsername())) {
                    result = wsConnection;
                }
            }
        }
        return result;
    }

    /**
     * Method used for broadcasting messages to all clients connected with
     * specified room (chess table)
     *
     * @param message
     * @param chessTableId
     */
    @Override
    public void broadcastToClientsInChessRoom(String message, Integer chessTableId) {
        Set<ClientMessageInbound> connections = findWSConnectionsByChessTable(chessTableId);
        for (ClientMessageInbound connection : connections) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message.toCharArray());
                WsOutbound wsOutbound = connection.getWsOutbound();
                if (wsOutbound != null) {
                    wsOutbound.writeTextMessage(buffer);
                    wsOutbound.flush();
                }
            } catch (IOException ignore) {
                logger.error("Exception during broadcasting to the chess table with id:" + chessTableId);
            }
        }
    }

    /**
     * Method used for broadcasting messages to all connected clients
     *
     * @param message
     */
    @Override
    public void broadcastToAllClients(String message) {
        // TODO
    }

    @Override
    public void createNewWSBinding(ClientMessageInbound wsClientConnection) {
        // TODO: check whether this wsConnections is really needed
        this.wsConnections.add(wsClientConnection);
    }
}
