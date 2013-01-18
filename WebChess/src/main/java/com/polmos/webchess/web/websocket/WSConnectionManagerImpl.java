package com.polmos.webchess.web.websocket;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
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
        wsConnections = new CopyOnWriteArraySet<ClientMessageInbound>();
        clientToRoomsMap = new HashMap<ClientMessageInbound, Set<Integer>>();
        chessTableIdToClientsMap = new HashMap<Integer, Set<ClientMessageInbound>>();
        logger.debug("WSConnectionManagerImpl created");
    }

    @Override
    public void addNewWSConnection(ClientMessageInbound wsClientConnection, Integer chessTableId) {
        wsConnections.add(wsClientConnection);
        Set<Integer> chessRoomIds = clientToRoomsMap.get(wsClientConnection);
        if (chessRoomIds == null) {
            chessRoomIds = new HashSet<Integer>();
        }
        chessRoomIds.add(chessTableId);
        clientToRoomsMap.put(wsClientConnection, chessRoomIds);
    }

    @Override
    public void removeWSConnection(ClientMessageInbound wsClientConnection) {
        wsConnections.remove(wsClientConnection);
    }

    @Override
    public Set<Integer> getChessTableIDs(ClientMessageInbound clientWS) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Set<ClientMessageInbound> findWSConnectionsToChessTable(Integer chessTableId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
