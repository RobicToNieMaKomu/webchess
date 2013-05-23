package com.polmos.webchess.matchmgnt.service;

import com.polmos.webchess.dao.ChessTableDAO;
import com.polmos.webchess.dao.MatchDAO;
import com.polmos.webchess.enums.GameStatus;
import com.polmos.webchess.enums.SupportedWSCommands;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessboardPojo;
import com.polmos.webchess.matchmgnt.entity.ChessTable;
import com.polmos.webchess.matchmgnt.entity.Match;
import com.polmos.webchess.matchmgnt.entity.User;
import com.polmos.webchess.service.ChessboardService;
import com.polmos.webchess.service.UserService;
import com.polmos.webchess.web.websocket.ClientMessageCreator;
import com.polmos.webchess.web.websocket.ClientMessageInbound;
import com.polmos.webchess.web.websocket.WSConnectionManager;
import java.util.ArrayList;
import java.util.Date;
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
    private ChessTableDAO chessTableDAO;
    @Autowired
    private WSConnectionManager wSConnectionManager;
    @Autowired
    private MatchExecutorService matchExecutorService;
    @Autowired
    private ChessboardService chessboardService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChessTableService chessTableService;

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
    public JSONObject processRoomStateRequest(Integer tableId) throws JSONException, WebChessException {
        // Only one match can be assigned to table in one time
        Match match = matchDAO.findMatchByTableId(tableId);
        Map<String, String> mapChessboard = null;
        Integer wpTime = DEFAULT_GAME_TIME;
        Integer bpTime = DEFAULT_GAME_TIME;
        String wPlayerName = "";
        String bPlayerName = "";
        Boolean gameInProgress = Boolean.FALSE;
        Set<String> spectators = new HashSet<>();
        Set<ClientMessageInbound> wsConnections = wSConnectionManager.findWSConnectionsByChessTable(tableId);
        for (ClientMessageInbound wsi : wsConnections) {
            spectators.add(wsi.getUsername());
        }
        if (match == null) {
            // If there is no match assigned to this table then send initial chessboard 
            ChessboardPojo newChessboard = chessboardService.createNewChessboard();
            mapChessboard = chessboardService.transformChessboardTableToMap(newChessboard);
            // Match hasnt started yet but there can be other player already sitting here
            ChessTable chessTable = chessTableDAO.findChessTableById(tableId);
            User bplayer = chessTable.getBplayer();
            User wplayer = chessTable.getWplayer();
            bPlayerName = (bplayer != null) ? bplayer.getLogin() : "";
            wPlayerName = (wplayer != null) ? wplayer.getLogin() : "";
        } else {
            bpTime = match.getBplayerTime();
            wpTime = match.getWplayerTime();
            wPlayerName = match.getWplayer().getLogin();
            bPlayerName = match.getBplayer().getLogin();
            gameInProgress = Boolean.TRUE;
        }
        // Pack players' names and times into auxiliary maps
        Map<String, String> playerNamesMap = createPlayerNamesMap(wPlayerName, bPlayerName);
        Map<String, Integer> playerTimesMap = createPlayerTimesMap(wpTime, bpTime);
        // Prepare and send response to all clients in room
        JSONObject result = ClientMessageCreator.createRoomStateMessage(tableId, playerNamesMap, spectators, mapChessboard, playerTimesMap, gameInProgress);
        return result;
    }

    @Override
    public JSONObject processSitRequest(Integer tableId, String color, String username) throws JSONException {
        JSONObject result = new JSONObject();
        // Only one match can be assigned to table in one time
        Match match = matchDAO.findMatchByTableId(tableId);
        // If match is already running then ignore this request
        if (match == null) {
            // Match hasnt started yet but there can be other player already sitting here
            ChessTable chessTable = chessTableDAO.findChessTableById(tableId);
            User wplayer = chessTable.getWplayer();
            User bplayer = chessTable.getBplayer();
            String wplayerName = "";
            String bplayerName = "";
            switch (color) {
                case SupportedWSCommands.WHITE:
                    wplayerName = (wplayer == null) ? username : wplayer.getLogin();
                    bplayerName = (bplayer != null) ? bplayer.getLogin() : "";
                    break;
                case SupportedWSCommands.BLACK:
                    bplayerName = (bplayer == null) ? username : bplayer.getLogin();
                    wplayerName = (wplayer != null) ? wplayer.getLogin() : "";
                    break;
            }
            // Update new users/usernames in the chessTable 
            User whitePlayer = userService.findUserByName(wplayerName);
            User blackUser = userService.findUserByName(bplayerName);
            chessTableService.updateStateOfChessTable(tableId, whitePlayer, blackUser, new Date());
            // Create response for the client
            result = ClientMessageCreator.createResponseToSitRequestMessage(tableId, createPlayerNamesMap(wplayerName, bplayerName));
        } else {
            // TODO: DC scenario?
        }
        return result;
    }

    @Override
    public JSONObject processStartRequest(Integer tableId, String username) throws JSONException, WebChessException {
        JSONObject result = new JSONObject();
        // Find chessTable with given tableId and associated users
        ChessTable chessTable = chessTableService.findChessTable(tableId);
        User wplayer = chessTable.getWplayer();
        User bplayer = chessTable.getBplayer();
        Match matchInProgress = matchDAO.findMatchInProgressByTableId(tableId);
        // If both players sit at the table and there is no game in progress then allow to start a new match
        if (wplayer != null && bplayer != null && matchInProgress == null) {
            Integer gameTime = chessTable.getGameTime();
            startNewMatch(wplayer, bplayer, gameTime);
            // Find all spectators watching this game
            Set<String> spectators = new HashSet<>();
            Set<ClientMessageInbound> wsConnections = wSConnectionManager.findWSConnectionsByChessTable(tableId);
            for (ClientMessageInbound wsi : wsConnections) {
                spectators.add(wsi.getUsername());
            }
            // Pack all necessary data into auxiliary maps
            ChessboardPojo newChessboard = chessboardService.createNewChessboard();
            Map<String, String> mapChessboard = chessboardService.transformChessboardTableToMap(newChessboard);
            Map<String, String> playerNamesMap = createPlayerNamesMap(wplayer.getLogin(), bplayer.getLogin());
            Map<String, Integer> playerTimesMap = createPlayerTimesMap(gameTime, gameTime);
            // Prepare and send response to all clients in room
            result = ClientMessageCreator.createRoomStateMessage(tableId, playerNamesMap, spectators, mapChessboard, playerTimesMap, Boolean.TRUE);
        }
        return result;
    }

    @Override
    public JSONObject processSurrenderRequest() {
        JSONObject result = new JSONObject();
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
    
    private Map<String, String> createPlayerNamesMap(String wplayerName, String bplayerName) {
        Map<String, String> result = new HashMap<>();
        result.put(SupportedWSCommands.WPLAYER, wplayerName);
        result.put(SupportedWSCommands.BPLAYER, bplayerName);
        return result;
    }
    
    private Map<String, Integer> createPlayerTimesMap(Integer wplayerTime, Integer bplayerTime) {
        Map<String, Integer> result = new HashMap<>();
        result.put(SupportedWSCommands.WPLAYER, wplayerTime);
        result.put(SupportedWSCommands.WPLAYER, bplayerTime);
        return result;
    }
}
