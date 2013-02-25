package com.polmos.webchess.web.websocket;

import com.polmos.webchess.enums.SupportedWSCommands;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public class ChatMessageCreator {

    private static final String DATE_FORMAT = "HH:mm:ss";

    public static String createChatMessage(String content, String username) {
        StringBuilder message = new StringBuilder();

        message.append("[<strong>");
        message.append(username);
        message.append("</strong>]: ");
        message.append(getSanitizedMessage(content));

        return message.toString();
    }

    /**
     * Creates message containing information about situation on the chessboard
     * and remaining players time.
     *
     * @param tableId - chess table id
     * @param chessboard - map containing field coordinates and chessman
     * @param wpTime - remaining white player time
     * @param bpTime - remaining black player time
     * @return filled JSON that can be send directly to client
     * @throws JSONException
     */
    public static JSONObject createChessboardStateMessage(Integer tableId, Map<String, String> chessboard, Integer wpTime, Integer bpTime) throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, Integer> remainingTimeMap = new HashMap<>();
        remainingTimeMap.put(SupportedWSCommands.WPLAYER, wpTime);
        remainingTimeMap.put(SupportedWSCommands.BPLAYER, bpTime);
        result.put(SupportedWSCommands.COMMAND, SupportedWSCommands.STATE);
        result.put(SupportedWSCommands.TABLE_ID, tableId);
        result.put(SupportedWSCommands.CHESSBOARD_STATE, chessboard);
        result.put(SupportedWSCommands.TIME, remainingTimeMap);
        // TODO: change to format {id, command, content (map/json)}
        return result;
    }
    /**
     * Creates message containing information about chess table.
     * 
     * @param tableId
     * @param wPlayerName
     * @param bPlayerName
     * @param spectators
     * @param proposedGameTime
     * @return filled JSON that can be send directly to client
     * @throws JSONException 
     */
    public static JSONObject createRoomStateMessage(Integer tableId, String wPlayerName, String bPlayerName, Set<String> spectators, Integer proposedGameTime) throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, String> players = new HashMap<>();
        Map<String, String> options = new HashMap<>();
        players.put(SupportedWSCommands.WPLAYER, wPlayerName);
        players.put(SupportedWSCommands.BPLAYER, bPlayerName);

        result.put(SupportedWSCommands.TABLE_ID, tableId);
        result.put(SupportedWSCommands.COMMAND, SupportedWSCommands.ROOM_STATE);
        result.put(SupportedWSCommands.PLAYERS, players);
        result.put(SupportedWSCommands.SPECTATORS, spectators);
        result.put(SupportedWSCommands.OPTIONS, options);
        result.put(SupportedWSCommands.TIME, proposedGameTime);
        
        return result;
    }

    /**
     * Get rid off html tags etc.
     *
     * @param rawMessage
     * @return
     */
    private static String getSanitizedMessage(String rawMessage) {
        String result = rawMessage;
        // TBD
        return result;
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String time = sdf.format(Calendar.getInstance().getTime());
        return time;
    }
}
