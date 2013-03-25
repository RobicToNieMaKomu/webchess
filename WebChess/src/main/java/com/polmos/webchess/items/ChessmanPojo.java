package com.polmos.webchess.items;

import com.polmos.webchess.enums.ChessmanEnum;
import com.polmos.webchess.enums.ColorsEnum;
import org.apache.log4j.Logger;

/**
 *
 * @author Piotrek
 */
public class ChessmanPojo {

    private static Logger logger = Logger.getLogger(ChessmanPojo.class);
    private ColorsEnum color;
    private ChessmanEnum chessmanType;
    private Integer rowPosition;
    private Integer columnPosition;

    public ChessmanPojo() {
        logger.debug("Chessman is rising up!");
    }

    public ChessmanEnum getChessmanType() {
        return chessmanType;
    }

    public void setChessmanType(ChessmanEnum chessmanType) {
        this.chessmanType = chessmanType;
    }

    public ColorsEnum getColor() {
        return color;
    }

    public void setColor(ColorsEnum color) {
        this.color = color;
    }

    public Integer getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(Integer columnPosition) {
        this.columnPosition = columnPosition;
    }

    public Integer getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(Integer rowPosition) {
        this.rowPosition = rowPosition;
    }
}
