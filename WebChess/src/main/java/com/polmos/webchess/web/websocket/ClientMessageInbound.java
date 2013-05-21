package com.polmos.webchess.web.websocket;

import com.polmos.webchess.enums.SupportedWSCommands;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.matchmgnt.service.MatchService;
import com.polmos.webchess.util.SpringContextProvider;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.logging.Level;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.WsOutbound;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

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
    private MatchService matchService;

    public ClientMessageInbound(String clientName) {
        this.username = clientName;
        wSConnectionManager = (WSConnectionManager) SpringContextProvider.applicationContext.getBean("wSConnectionManager");
        matchService = (MatchService) SpringContextProvider.applicationContext.getBean("matchService");
        if (wSConnectionManager == null || matchService == null) {
            logger.error("WS Connection Manager or Match service not initialized for a client:" + this);
        }
    }

    @Override
    protected void onOpen(WsOutbound outbound) {
        logger.debug("WebSocket for client: " + username + " opened.");
    }

    @Override
    protected void onClose(int status) {
        Set<Integer> chessTableIDs = wSConnectionManager.getChessTableIDs(this);
        wSConnectionManager.removeWSConnection(this);
        String message = String.format("* %s %s", username, "has disconnected.");
        // Broadcast message to the all chess rooms in which this client was sitting
        for (Integer chessTableId : chessTableIDs) {
            wSConnectionManager.broadcastToClientsInChessRoom(message, chessTableId);
        }
    }

    @Override
    protected void onTextMessage(CharBuffer message) throws IOException {
        try {
            // Never trust the client (and women)
            JSONObject json = sanitizeMessage(message.toString());
            int tableId = json.getInt(SupportedWSCommands.TABLE_ID);
            String command = json.getString(SupportedWSCommands.COMMAND);
            String content = json.getString(SupportedWSCommands.CONTENT);

            switch (command) {
                case SupportedWSCommands.CHAT:
                    String chatMessage = ClientMessageCreator.createChatMessage(content, this.username);
                    wSConnectionManager.broadcastToClientsInChessRoom(chatMessage, tableId);
                    break;
                case SupportedWSCommands.DRAW:
                    break;
                case SupportedWSCommands.MOVE:
                    break;
                case SupportedWSCommands.OPTIONS:
                    break;
                case SupportedWSCommands.READY:
                    break;
                case SupportedWSCommands.SIT:
                    JSONObject sitResponse = matchService.processSitRequest(tableId, content, username);
                    wSConnectionManager.broadcastToClientsInChessRoom(sitResponse.toString(), tableId);
                    break;
                case SupportedWSCommands.CHESSBOARD_STATE:
                    JSONObject chessboardStateResponse = matchService.processChessboardStateRequest(tableId);
                    sendJSONMessage(chessboardStateResponse);
                    break;
                case SupportedWSCommands.ROOM_STATE:
                    JSONObject roomStateResponse = matchService.processRoomStateRequest(tableId);
                    sendJSONMessage(roomStateResponse);
                    break;
                case SupportedWSCommands.SURRENDER:
                    break;
                default:
                    logger.error("Not supported type of a WS command:" + command);
            }
        } catch (JSONException | WebChessException ex) {
            logger.error("An exception occured while received message from client:" + this.username);
            logger.error(ex);
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

    private void sendJSONMessage(JSONObject message) {
        try {
            CharBuffer buffer = CharBuffer.wrap(message.toString().toCharArray());
            WsOutbound wsOutbound = getWsOutbound();
            if (wsOutbound != null) {
                wsOutbound.writeTextMessage(buffer);
                wsOutbound.flush();
            }
        } catch (IOException ex) {
            logger.debug("Error during sending message to the client:" + ex);
        }
    }

    /**
     * Method sanitizes message and checks whether has TABLE_ID, COMMAND and
     * CONTENT within. Format of the expected messages:
     * "TABLEID:<id>,COMMAND:'<command>',CONTENT:'<content>'" Examples:
     * "TABLEID:1,COMMAND:'ROOM_STATE',CONTENT:''"
     * "TABLEID:2,COMMAND:'CHAT',CONTENT:'What a splendid day gentlemen!'"
     *
     * @param message
     * @return List containing table id and body
     */
    private JSONObject sanitizeMessage(String message) throws JSONException {
        JSONObject result = null;
        if (message != null && !message.isEmpty()) {
            // Check whether message starts with expected prefix
            Boolean hasTableId = message.contains(SupportedWSCommands.TABLE_ID);
            Boolean hasCommand = message.contains(SupportedWSCommands.COMMAND);
            Boolean hasContent = message.contains(SupportedWSCommands.CONTENT);
            // If and only if message contains tableId, command and content try to parse it
            if (hasTableId && hasCommand && hasContent) {
                result = new JSONObject(message);
            } else {
                throw new JSONException("Unexpected message: " + message + "\n");
            }
        }
        return result;
    }
}
