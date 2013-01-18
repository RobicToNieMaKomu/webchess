/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.polmos.webchess.web.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class handles messages/data incoming from chess rooms (directly from guests) 
 * 
 * @author RobicToNieMaKomu
 */
public class ClientMessageInbound extends MessageInbound {

    private final String nickname;
    private static final String GUEST_PREFIX = "Guest";
    @Autowired
    private WSConnectionManager wSConnectionManager;

    public ClientMessageInbound(int id) {
        this.nickname = GUEST_PREFIX + id;
    }

    @Override
    protected void onOpen(WsOutbound outbound) {
        wSConnectionManager.addNewWSConnection(this, Integer.MIN_VALUE);
        String message = String.format("* %s %s", nickname, "has joined.");
        broadcast(message);
    }

    @Override
    protected void onClose(int status) {
        wSConnectionManager.removeWSConnection(this);
        String message = String.format("* %s %s", nickname, "has disconnected.");
        broadcast(message);
    }

    @Override
    protected void onBinaryMessage(ByteBuffer message) throws IOException {
        throw new UnsupportedOperationException(
                "Binary message not supported.");
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
        // Never trust the client
        String filteredMessage = message.toString();
        broadcast(filteredMessage);
    }

    private void broadcast(String message) {
        /*for (ClientMessageInbound connection : connections) {
            try {
                CharBuffer buffer = CharBuffer.wrap(message);
                connection.getWsOutbound().writeTextMessage(buffer);
            } catch (IOException ignore) {
                // Ignore
            }
        }*/
    }
}

