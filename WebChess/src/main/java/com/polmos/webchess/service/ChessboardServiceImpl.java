package com.polmos.webchess.service;

import com.polmos.webchess.enums.ChessmanEnum;
import com.polmos.webchess.enums.ColorsEnum;
import com.polmos.webchess.enums.ColumnEnum;
import com.polmos.webchess.enums.RowEnum;
import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessboardPojo;
import com.polmos.webchess.items.ChessmanPojo;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Piotrek
 */
@Service(value = "chessboardService")
public class ChessboardServiceImpl implements ChessboardService {

    @Resource(name = "chessmanFactory")
    private ChessmanFactory chessmanFactory;
    private ChessboardPojo chessboard;
    private static Logger logger = Logger.getLogger(ChessboardServiceImpl.class);
    private final Integer PAWN_COUNT = 8;
    private final Integer ROW_COUNT = 8;
    private final Integer FIELDS_COUNT = 64;
    private final String COLON = ":";
    private final String DASH = "_";

    public ChessboardServiceImpl() {
        logger.debug("ChessboardServiceImpl is creating...");
    }

    @Override
    public ChessboardPojo createNewChessboard() {
        ChessboardPojo desk = new ChessboardPojo();
        logger.debug("Creating new chessboard");
        initialSetUp(desk);
        return desk;
    }

    private void initialSetUp(ChessboardPojo chessboard) {
        logger.debug("Creating white chessmans");
        ChessmanPojo[][] newChessboard = chessboard.getChessBoard();
        // White chessmen
        ChessmanPojo aRook = chessmanFactory.createRook(ColorsEnum.WHITE, ColumnEnum.A.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo bRook = chessmanFactory.createRook(ColorsEnum.WHITE, ColumnEnum.H.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo aKnight = chessmanFactory.createKnight(ColorsEnum.WHITE, ColumnEnum.B.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo bKnight = chessmanFactory.createKnight(ColorsEnum.WHITE, ColumnEnum.G.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo aBishop = chessmanFactory.createBishop(ColorsEnum.WHITE, ColumnEnum.C.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo bBishop = chessmanFactory.createBishop(ColorsEnum.WHITE, ColumnEnum.F.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo queen = chessmanFactory.createQueen(ColorsEnum.WHITE, ColumnEnum.D.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        ChessmanPojo king = chessmanFactory.createKing(ColorsEnum.WHITE, ColumnEnum.E.getColumnPosition(),
                RowEnum.ONE.getRowNumber());
        // Set up those chessmen at the chessboard
        newChessboard[ColumnEnum.A.getColumnPosition()][RowEnum.ONE.getRowNumber()] = aRook;
        newChessboard[ColumnEnum.B.getColumnPosition()][RowEnum.ONE.getRowNumber()] = aKnight;
        newChessboard[ColumnEnum.C.getColumnPosition()][RowEnum.ONE.getRowNumber()] = aBishop;
        newChessboard[ColumnEnum.D.getColumnPosition()][RowEnum.ONE.getRowNumber()] = queen;
        newChessboard[ColumnEnum.E.getColumnPosition()][RowEnum.ONE.getRowNumber()] = king;
        newChessboard[ColumnEnum.F.getColumnPosition()][RowEnum.ONE.getRowNumber()] = bBishop;
        newChessboard[ColumnEnum.G.getColumnPosition()][RowEnum.ONE.getRowNumber()] = bKnight;
        newChessboard[ColumnEnum.H.getColumnPosition()][RowEnum.ONE.getRowNumber()] = bRook;
        // White pawns
        for (int i = 0; i < PAWN_COUNT; i++) {
            ChessmanPojo pawn = chessmanFactory.createPawn(ColorsEnum.WHITE, i, RowEnum.TWO.getRowNumber());
            newChessboard[i][RowEnum.TWO.getRowNumber()] = pawn;
        }
        // Black chessmen
        ChessmanPojo acRook = chessmanFactory.createRook(ColorsEnum.BLACK, ColumnEnum.A.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo bcRook = chessmanFactory.createRook(ColorsEnum.BLACK, ColumnEnum.H.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo acKnight = chessmanFactory.createKnight(ColorsEnum.BLACK, ColumnEnum.B.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo bcKnight = chessmanFactory.createKnight(ColorsEnum.BLACK, ColumnEnum.G.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo acBishop = chessmanFactory.createBishop(ColorsEnum.BLACK, ColumnEnum.C.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo bcBishop = chessmanFactory.createBishop(ColorsEnum.BLACK, ColumnEnum.F.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo cqueen = chessmanFactory.createQueen(ColorsEnum.BLACK, ColumnEnum.D.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        ChessmanPojo cking = chessmanFactory.createKing(ColorsEnum.BLACK, ColumnEnum.E.getColumnPosition(),
                RowEnum.EIGHT.getRowNumber());
        // Set up those chessmen at the chessboard
        newChessboard[ColumnEnum.A.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = acRook;
        newChessboard[ColumnEnum.B.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = acKnight;
        newChessboard[ColumnEnum.C.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = acBishop;
        newChessboard[ColumnEnum.D.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = cqueen;
        newChessboard[ColumnEnum.E.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = cking;
        newChessboard[ColumnEnum.F.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = bcBishop;
        newChessboard[ColumnEnum.G.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = bcKnight;
        newChessboard[ColumnEnum.H.getColumnPosition()][RowEnum.EIGHT.getRowNumber()] = bcRook;
        // Black pawns
        for (int i = 0; i < PAWN_COUNT; i++) {
            ChessmanPojo cpawn = chessmanFactory.createPawn(ColorsEnum.BLACK, i, RowEnum.SEVEN.getRowNumber());
            newChessboard[i][RowEnum.SEVEN.getRowNumber()] = cpawn;
        }
    }

    @Override
    public ChessboardPojo getDesk() {
        return this.chessboard;
    }

    @Override
    public void setDesk(ChessboardPojo chessboard) {
        this.chessboard = chessboard;
    }

    @Override
    public String[] transformChessboardToArray(ChessboardPojo chessboard) {
        String[] arrayChessboard = new String[FIELDS_COUNT];
        ChessmanPojo[][] chessBoard = chessboard.getChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StringBuilder field = new StringBuilder();
                ChessmanPojo chessman = chessBoard[i][j];
                if (chessman != null) {
                    Integer columnPosition = chessman.getColumnPosition();
                    Integer rowPosition = chessman.getRowPosition();
                    String colorName = chessman.getColor().getColorName();
                    String type = chessman.getChessmanType().name();
                    field.append(columnPosition);
                    field.append(rowPosition);
                    field.append(COLON);
                    field.append(colorName);
                    field.append(DASH);
                    field.append(type);
                }
                arrayChessboard[i * 8 + j] = field.toString();
            }
        }
        return arrayChessboard;
    }

    @Override
    public Map<String, String> transformChessboardTableToMap(ChessboardPojo chessboard) throws WebChessException {
        Map<String, String> result = new HashMap<>();
        logger.debug("Transforming chessboard POJO to map");
        if (chessboard != null) {
            ChessmanPojo[][] chessBoard = chessboard.getChessBoard();
            if (chessBoard != null && chessBoard.length == ROW_COUNT) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessmanPojo pojo = chessBoard[i][j];
                        String jsonValue = transformPojoToJSONString(pojo);
                        //String columnName = ColumnEnum.convertColumnPosition(i).getColumnNameLowerCase();
                        //String rowName = RowEnum.convertRowPosition(j).getRowNumber().toString();
                        String columnName = String.valueOf(i);
                        String rowName = String.valueOf(j);
                        result.put(columnName+rowName, jsonValue);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Transforms chessman POJO to string value that can be recognized by
     * client's JS parser. Example: White King -> 'wking'; Black Pawn -> 'bp'
     *
     * @param pojo
     * @return String value
     */
    private String transformPojoToJSONString(ChessmanPojo pojo) {
        String result = "";
        if (pojo != null) {
            ChessmanEnum chessmanType = pojo.getChessmanType();
            ColorsEnum color = pojo.getColor();
            logger.debug("Transforming chessman: " + color + " " + chessmanType + " to JSON property");
            result = pojo.getColor().getShortColorName();
            switch (chessmanType) {
                case BISHOP:
                    result = result.concat("b");
                    break;
                case KING:
                    result = result.concat("king");
                    break;
                case KNIGHT:
                    result = result.concat("k");
                    break;
                case PAWN:
                    result = result.concat("c");
                    break;
                case QUEEN:
                    result = result.concat("q");
                    break;
                case ROOK:
                    result = result.concat("r");
                    break;
                default:
                    logger.debug("Unknown chessman type: " + chessmanType);
                    break;
            }
        }
        return result;
    }
    
    @Override
    public String[][] serializeChessboard(ChessboardPojo chessboard) {
        String[][] result = new String[ROW_COUNT][ROW_COUNT];
        ChessmanPojo[][] chessBoard = chessboard.getChessBoard();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessmanPojo chessman = chessBoard[i][j];
                String jsonValue = transformPojoToJSONString(chessman);
                result[i][j] = jsonValue;
            }
        }
        return result;
    }
}
