package com.polmos.webchess.exceptions;

import com.polmos.webchess.enums.ExceptionsEnum;

/**
 *
 * @author Piotrek
 */
public class WebChessException extends Exception {

    private String exceptionName;
    private ExceptionsEnum exceptionType;

    public ExceptionsEnum getExceptionType() {
        return exceptionType;
    }

    public void setExceptionType(ExceptionsEnum exceptionType) {
        this.exceptionType = exceptionType;
    }

    public WebChessException() {
        super();
        exceptionType = ExceptionsEnum.UNDEFINIED;
    }

    public WebChessException(String exception) {
        super(exception);
        exceptionName = exception;
        exceptionType = ExceptionsEnum.UNDEFINIED;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }
}
