package com.polmos.webchess.web.websocket;

import com.polmos.webchess.util.SpringContextProvider;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;

/**
 * This class handles messages/data incoming from chess rooms (directly from
 * guests)
 *
 * @author RobicToNieMaKomu
 */
public class ClientMessageInbound extends MessageInbound {

    private static final Logger logger = Logger.getLogger(ClientMessageInbound.class);
    private static final String MESSAGE_PREFIX = "tableId:";
    private static final String SEMICOLON = ";";
    private final String username;
    private WSConnectionManager wSConnectionManager;

    public ClientMessageInbound(String clientName) {
        this.username = clientName;
        wSConnectionManager = (WSConnectionManager) SpringContextProvider.applicationContext.getBean("wSConnectionManager");
        if (wSConnectionManager == null) {
            logger.error("WS Connection Manager not initialized for a client:" + this);
        }
    }

    @Override
    protected void onOpen(WsOutbound outbound) {
        logger.debug("WebSocket for client: " + username + " opened.");
        String message = String.format("* %s %s", username, "has joined.");
        // wSConnectionManager.broadcastToClientsInChessRoom(message, null); // TODO table id needed!
        // CharBuffer buff = null;buff.append("sxds");
        //outbound.writeTextMessage(message.toCharArray());

    }

    @Override
    protected void onClose(int status) {
        wSConnectionManager.removeWSConnection(this);
        String message = String.format("* %s %s", username, "has disconnected.");
        // Broadcast message to the all chess rooms in which this client was sitting
        Set<Integer> chessTableIDs = wSConnectionManager.getChessTableIDs(this);
        for (Integer chessTableId : chessTableIDs) {
            wSConnectionManager.broadcastToClientsInChessRoom(message, chessTableId);
        }
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
        // Never trust the client (or women)
        String filteredMessage = message.toString();
        List<String> sanitizedMessage = sanitizeMessage(filteredMessage);
        if (sanitizedMessage.size() == 2) {
            // Retrieve table id from where this message was sent by client
            String tabId = sanitizedMessage.get(0);
            String content = sanitizedMessage.get(1);

            Integer tableId = Integer.parseInt(tabId);
            wSConnectionManager.broadcastToClientsInChessRoom(content, tableId);
        }
    }

    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
        throw new UnsupportedOperationException(
                "Binary message not supported. Yet.");
    }

    public String getUsername() {
        return this.username;
    }

    /**
     * Method sanitizes and splits messeage into two parts - table id from where
     * this message came from and body containing information to be displayed.
     * Expected message: 
     * "tableId:<id>;<content>" "tableId:50;Hello all! What a splendid day gentlemen!"
     *
     * @param message
     * @return List containing table id and body
     */
    private List<String> sanitizeMessage(String message) {
        List<String> result = new ArrayList<String>();
        if (message != null && !message.isEmpty()) {
            // Check whether message starts with expected prefix
            Boolean hasPrefix = message.startsWith(MESSAGE_PREFIX);
            // Check whether message contains at least one semicolon
            Boolean hasSemicolon = message.contains(SEMICOLON);
            // Check whether there is at least 1 char between semicolon and prefix 
            // (representing table id)
            Boolean hasTableId = false;
            int indexOfSemicolon = message.indexOf(SEMICOLON);
            if (indexOfSemicolon - MESSAGE_PREFIX.length() >= 1) {
                hasTableId = true;
            }
            // Check whether this message has content because
            // there is no sense to distribute empty messages
            Boolean hasContent = false;
            if (message.length() - (indexOfSemicolon + 1) > 0) {
                hasContent = true;
            }
            // Only if all these four conditionals are true, message is valid
            if (hasPrefix && hasTableId && hasSemicolon && hasContent) {
                result.add(message.substring(MESSAGE_PREFIX.length(), indexOfSemicolon));
                result.add(message.substring(indexOfSemicolon + 1, message.length()));
            } else {
                logger.warn("Received message is invalid: " + message);
                // TODO: Exception handling?
            }
        }
        return result;
    }
}
