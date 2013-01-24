package com.polmos.webchess.web.websocket;

import com.polmos.webchess.util.SpringContextProvider;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;

/**
 * This class handles messages/data incoming from chess rooms (directly from guests) 
 * 
 * @author RobicToNieMaKomu
 */
public class ClientMessageInbound extends MessageInbound {

    private static final Logger logger = Logger.getLogger(ClientMessageInbound.class);
    private final String nickname;
    private static final String TABLE_ID_PREFIX = "chessTableId=";
    private static final String GUEST_PREFIX = "Guest";
    private WSConnectionManager wSConnectionManager;

    public ClientMessageInbound(int clientId) {
        this.nickname = GUEST_PREFIX + clientId;
        wSConnectionManager = (WSConnectionManager) SpringContextProvider.applicationContext.getBean("wSConnectionManager");
        if (wSConnectionManager == null) {
            logger.error("WS Connection Manager not initialized for a client:"+this);
        }
    }

    @Override
    protected void onOpen(WsOutbound outbound) {
        wSConnectionManager.addNewWSConnection(this, Integer.MIN_VALUE);
        String message = String.format("* %s %s", nickname, "has joined.");
        broadcastToClientsInChessRoom(message, null); // TODO table id needed! 
    }

    @Override
    protected void onClose(int status) {
        wSConnectionManager.removeWSConnection(this);
        String message = String.format("* %s %s", nickname, "has disconnected.");
        broadcastToClientsInChessRoom(message, status);
    }

    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
        throw new UnsupportedOperationException(
                "Binary message not supported.");
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
        // Never trust the client or women (unfortunately we have only one option here)
        String filteredMessage = message.toString();
        broadcastToClientsInChessRoom(filteredMessage, null); // TODO table id needed! 
    }

    /**
     * Method used for broadcasting messages to all clients connected with
     * specified room (chess table)
     * 
     * @param message
     * @param chessTableId 
     */
    private void broadcastToClientsInChessRoom(String message, Integer chessTableId) {
        Set<ClientMessageInbound> connections = wSConnectionManager.findWSConnectionsToChessTable(chessTableId);
        for (ClientMessageInbound connection : connections) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message);
                connection.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException ignore) {
                logger.error("Exception during broadcasting to the chess table with id:"+chessTableId);
            }
        }
    }
    
    /**
     * Method used for broadcasting messages to all connected clients 
     * 
     * @param message 
     */
    private void broadcastToAllClients(String message) {
        // TODO
    }
    
    private String sanitizeQueryString(String queryString) {
        String result = "";
        // Only chessTableId is expected
        if (queryString != null && queryString.startsWith(TABLE_ID_PREFIX)) {
            String value = queryString.replaceFirst(TABLE_ID_PREFIX, "");
            String regex = "[0-9]*";
            if (value.matches(regex)) {
                result = value;
            }
        }
        return result;
    }
}

