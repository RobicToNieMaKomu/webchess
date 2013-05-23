package com.polmos.webchess.web.websocket;

import com.polmos.webchess.enums.SupportedWSCommands;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public class ClientMessageCreator {

    private static final String DATE_FORMAT = "HH:mm:ss";
    private static final Integer NA = -1;

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
     * @param tableId
     * @param chessboard
     * @param wpTime
     * @param bpTime
     * @return
     * @throws JSONException
     */
    public static JSONObject createChessboardStateMessage(Integer tableId, Map<String, String> chessboard, Map<String, Integer> playersTime) throws JSONException {
        JSONObject result = createRoomStateMessage(tableId, new HashMap<String, String>(), new HashSet<String>(), chessboard, playersTime, Boolean.TRUE);
        result.put(SupportedWSCommands.COMMAND, SupportedWSCommands.CHESSBOARD_STATE);
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
     * @return filled JSON that can be sent directly to client
     * @throws JSONException
     */
    public static JSONObject createRoomStateMessage(Integer tableId, Map<String, String> playerNamesMap, Set<String> spectators, Map<String, String> chessboard, Map<String, Integer> playersTime, Boolean gameInProgress) throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, String> players = new HashMap<>();
        Map<String, String> options = new HashMap<>();
        Map<String, Integer> remainingTimeMap = new HashMap<>();

        players.put(SupportedWSCommands.WPLAYER, playerNamesMap.get(SupportedWSCommands.WPLAYER));
        players.put(SupportedWSCommands.BPLAYER, playerNamesMap.get(SupportedWSCommands.BPLAYER));

        remainingTimeMap.put(SupportedWSCommands.WPLAYER, playersTime.get(SupportedWSCommands.WPLAYER));
        remainingTimeMap.put(SupportedWSCommands.BPLAYER, playersTime.get(SupportedWSCommands.BPLAYER));

        result.put(SupportedWSCommands.TABLE_ID, tableId);
        result.put(SupportedWSCommands.COMMAND, SupportedWSCommands.ROOM_STATE);
        result.put(SupportedWSCommands.CHESSBOARD_STATE, chessboard);
        result.put(SupportedWSCommands.PLAYERS, players);
        result.put(SupportedWSCommands.TIME, remainingTimeMap);
        result.put(SupportedWSCommands.SPECTATORS, spectators);
        result.put(SupportedWSCommands.OPTIONS, options);
        result.put(SupportedWSCommands.IN_PROGRESS, gameInProgress);

        return result;
    }

    public static JSONObject createResponseToSitRequestMessage(Integer tableId, Map<String, String> playersNameMap) throws JSONException {
        JSONObject result = createRoomStateMessage(tableId, playersNameMap, new HashSet<String>(), new HashMap<String, String>(), new HashMap<String, Integer>(), Boolean.FALSE);
        result.put(SupportedWSCommands.COMMAND, SupportedWSCommands.SIT);
        return result;
    }

    /**
     * Get rid off HTML tags etc.
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
