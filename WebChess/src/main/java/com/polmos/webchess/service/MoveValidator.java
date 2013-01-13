package com.polmos.webchess.service;

import com.polmos.webchess.exceptions.WebChessException;
import com.polmos.webchess.items.ChessmanPojo;

/**
 *
 * @author Piotrek
 */
public interface MoveValidator {

    Boolean validateMove(ChessmanPojo chessman, Integer endRowPosition, Integer endColumnPosition) throws WebChessException;
    }
