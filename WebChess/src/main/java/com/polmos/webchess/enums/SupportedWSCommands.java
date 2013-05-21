package com.polmos.webchess.enums;

/**
 *
 * @author RobicToNieMaKomu
 */
public class SupportedWSCommands {
    // JSON fields
    public static final String COMMAND = "COMMAND";
    public static final String CONTENT = "CONTENT";
    public static final String TABLE_ID = "TABLEID";
    // Expected commands from client
    public static final String MOVE = "MOVE";
    public static final String OPTIONS = "OPTIONS";
    public static final String SIT = "SIT";
    public static final String READY = "READY";
    public static final String DRAW = "DRAW";
    public static final String SURRENDER = "SURRENDER";
    // Common commands 
    public static final String CHAT = "CHAT";
    public static final String WPLAYER = "WPLAYER";
    public static final String BPLAYER = "BPLAYER";
    // Describes current position on the chessboard
    public static final String CHESSBOARD_STATE = "CHESSBOARD_STATE";
    // Describes current game preferencies, players, colors, etc..
    public static final String ROOM_STATE = "ROOM_STATE";
    // Supported commands to client
    public static final String PLAYERS = "PLAYERS";
    public static final String SPECTATORS = "SPECTATORS";
    // Remaining players time
    public static final String TIME = "TIME";
    // Announces end of the game
    public static final String END = "END";
    // Other commands
    public static final String WHITE = "WHITE";
    public static final String BLACK = "BLACK";
}
