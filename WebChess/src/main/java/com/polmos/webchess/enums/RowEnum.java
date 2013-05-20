package com.polmos.webchess.enums;

import com.polmos.webchess.exceptions.WebChessException;

/**
 *
 * @author Piotrek
 */
public enum RowEnum {

    ONE(0), TWO(1), THREE(2), FOUR(3), FIVE(4), SIX(5), SEVEN(6), EIGHT(7);
    private Integer rowNumber;

    RowEnum(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
    
    public String getRowNameLowerCase() {
        return this.toString().toLowerCase();
    }
    
     public static RowEnum convertRowPosition(Integer row) throws WebChessException {
        switch (row) {
            case 0:
                return ONE;
            case 1:
                return TWO;
            case 2:
                return THREE;
            case 3:
                return FOUR;
            case 4:
                return FIVE;
            case 5:
                return SIX;
            case 6:
                return SEVEN;
            case 7:
                return EIGHT;
            default:
                WebChessException webEx = new WebChessException();
                webEx.setExceptionType(ExceptionsEnum.OUT_OF_RANGE);
                throw webEx;
        }
     }
}
