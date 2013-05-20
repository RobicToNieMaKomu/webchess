package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.dao.MatchDAO;
import com.polmos.webchess.enums.GameStatus;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessboardPojo;
import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.entity.User;
import com.polmos.webchess.service.ChessboardService;
import com.polmos.webchess.web.websocket.ClientMessageCreator;
import com.polmos.webchess.web.websocket.WSConnectionManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RobicToNieMaKomu
 */
@Service("matchService")
public class MatchServiceImpl implements MatchService {

    private static Logger logger = Logger.getLogger(MatchServiceImpl.class);
    private final Integer DEFAULT_GAME_TIME = 900;
    @Autowired
    private MatchDAO matchDAO;
    @Autowired
    private WSConnectionManager wSConnectionManager;
    @Autowired
    private MatchExecutorService matchExecutorService;
    @Autowired
    private ChessboardService chessboardService;

    public MatchServiceImpl() {
    }

    @Override
    public List<Match> findAllMatches() {
        List<Match> result = new ArrayList<>();
        return result;
    }

    @Override
    public void startNewMatch(User wplayer, User bplayer, Integer gameTime) {
        Match match = new Match();
        match.setWplayer(wplayer);
        match.setBplayer(bplayer);
        match.setWplayerTime(gameTime);
        match.setBplayerTime(gameTime);
        match.setProgress(GameStatus.WHITE_MOVE);
        match.setHasended(false);
        Integer matchId = matchDAO.saveMatch(match);
        matchExecutorService.start(matchId);
    }

    @Override
    public Match findMatchById(Integer matchId) {
        Match match = matchDAO.findMatchById(matchId);
        return match;
    }

    @Override
    public void pushMatchStatusToPlayers(Match match) {
        try {
            // Kinda wird methods chain...TODO: fix this
            Integer tableId = match.getTableid().getTableId();
            JSONObject message = ClientMessageCreator.createChessboardStateMessage(tableId, null, match.getWplayerTime(), match.getBplayerTime());
            wSConnectionManager.broadcastToClientsInChessRoom(message.toString(), tableId);
        } catch (JSONException ex) {
            logger.error("Error during pushing chessboard state to players:" + ex);
        }
    }

    @Override
    public JSONObject processRoomStateRequest(Integer tableId) throws JSONException, WebChessException {
        // Only one match can be assigned to table in one time
        Match match = matchDAO.findMatchByTableId(tableId);
        Map<String, String> mapChessboard = null;
        Integer wpTime = DEFAULT_GAME_TIME;
        Integer bpTime = DEFAULT_GAME_TIME;
        String wPlayerName = "";
        String bPlayerName = "";
        Set<String> spectators = new HashSet<>();
        if (match == null) {
            // If there is no match assigned to this table, then associate TEMPLATE 
            // (default game time, no players, etc ...)
            ChessboardPojo newChessboard = chessboardService.createNewChessboard();
            mapChessboard = chessboardService.transformChessboardTableToMap(newChessboard);
        } else {
            bpTime = match.getBplayerTime();
            wpTime = match.getWplayerTime();
            wPlayerName = match.getWplayer().getLogin();
            bPlayerName = match.getBplayer().getLogin();
            // TBD: spectators
        }
        JSONObject result = ClientMessageCreator.createRoomStateMessage(tableId, wPlayerName, bPlayerName, spectators, mapChessboard, wpTime, bpTime);
        return result;
    }

    @Override
    public JSONObject processChessboardStateRequest(Integer tableId) throws JSONException {
        JSONObject result = new JSONObject();
        
        return result;
    }

    @Override
    public JSONObject processChatRequest() {
        JSONObject result = new JSONObject();
        return result;
    }

    @Override
    public JSONObject processDrawRequest() {
        JSONObject result = new JSONObject();
        return result;
    }

    @Override
    public JSONObject processMoveRequest() {
        JSONObject result = new JSONObject();
        return result;
    }

    @Override
    public JSONObject processOptionsRequest() {
        JSONObject result = new JSONObject();
        return result;
    }

    @Override
    public JSONObject processReadyRequest() {
        JSONObject result = new JSONObject();
        return result;
    }

    @Override
    public JSONObject processSitRequest() {
        JSONObject result = new JSONObject();
        return result;
    }

    @Override
    public JSONObject processSurrenderRequest() {
        JSONObject result = new JSONObject();
        return result;
    }
}
