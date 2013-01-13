package com.polmos.webchess.service;

import com.polmos.webchess.enums.ChessmanEnum;
import com.polmos.webchess.enums.ColorsEnum;
import com.polmos.webchess.items.ChessmanPojo;

/**
 *
 * @author Piotrek
 */
public interface ChessmanFactory {

    ChessmanPojo createPawn(ColorsEnum color, Integer columnPosition, Integer rowPosition);

    ChessmanPojo createKnight(ColorsEnum color, Integer columnPosition, Integer rowPosition);

    ChessmanPojo createBishop(ColorsEnum color, Integer columnPosition, Integer rowPosition);

    ChessmanPojo createRook(ColorsEnum color, Integer columnPosition, Integer rowPosition);

    ChessmanPojo createQueen(ColorsEnum color, Integer columnPosition, Integer rowPosition);

    ChessmanPojo createKing(ColorsEnum color, Integer columnPosition, Integer rowPosition);
}
