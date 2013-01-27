package com.polmos.webchess.web.websocket;

import com.polmos.webchess.util.SpringContextProvider;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
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
        String message = String.format("* %s %s", username, "has joined.");
        wSConnectionManager.broadcastToClientsInChessRoom(message, null); // TODO table id needed! 
    }

    @Override
    protected void onClose(int status) {
        wSConnectionManager.removeWSConnection(this);
        String message = String.format("* %s %s", username, "has disconnected.");
        wSConnectionManager.broadcastToClientsInChessRoom(message, status);
    }

    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
        throw new UnsupportedOperationException(
                "Binary message not supported. Yet.");
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
        // Never trust the client (or women)
        String filteredMessage = message.toString();
        wSConnectionManager.broadcastToClientsInChessRoom(filteredMessage, null); // TODO table id needed! 
    }

    public String getUsername() {
        return this.username;
    }
}
