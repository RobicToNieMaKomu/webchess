package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.entity.User;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author RobicToNieMaKomu
 */
public interface MatchService {

    List<Match> findAllMatches();

    void startNewMatch(User wplayer, User bplayer, Integer gameTime);

    Match findMatchById(final Integer matchId);

    void pushMatchStatusToPlayers(Match match);

    // ----- Client requests handling -----
    /**
     * Method creates response containing all necessary information about
     * current room (with tableId). Returned JSON includes players time, colors,
     * options etc.
     *
     * @param tableId - id of the chess room/table
     * @return JSON response for the client
     */
    JSONObject processRoomStateRequest(Integer tableId) throws JSONException, WebChessException;

    /**
     * Method creates response containing game state (current position on the
     * chessboard). Only tableId is needed because only one game can be played
     * at the one moment.
     *
     * @param tableId
     * @return JSON response for the client
     */
    JSONObject processChessboardStateRequest(Integer tableId) throws JSONException;

    JSONObject processChatRequest();

    JSONObject processDrawRequest();

    JSONObject processMoveRequest();

    JSONObject processOptionsRequest();

    JSONObject processReadyRequest();

    JSONObject processSitRequest();

    JSONObject processSurrenderRequest();
}
