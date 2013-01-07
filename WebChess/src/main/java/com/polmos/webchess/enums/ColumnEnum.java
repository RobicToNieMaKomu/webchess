package com.polmos.webchess.enums;

import com.polmos.webchess.exceptions.WebChessException;

/**
 *
 * @author Piotrek
 */
public enum ColumnEnum {

    A("A", 0), B("B", 1), C("C", 2), D("D", 3), E("E", 4), F("F", 5), G("G", 6), H("H", 7);

    ColumnEnum(String column, Integer columnPosition) {
        this.column = column;
        this.columnPosition = columnPosition;
    }
    private String column;
    private Integer columnPosition;

    public Integer getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(Integer columnPosition) {
        this.columnPosition = columnPosition;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public static ColumnEnum convertColumnPosition(Integer column) throws WebChessException {
        switch (column) {
            case 0:
                return A;
            case 1:
                return B;
            case 2:
                return C;
            case 3:
                return D;
            case 4:
                return E;
            case 5:
                return F;
            case 6:
                return G;
            case 7:
                return H;
            default:
                WebChessException webEx = new WebChessException();
                webEx.setExceptionType(ExceptionsEnum.OUT_OF_RANGE);
                throw webEx;
        }
    }
}
