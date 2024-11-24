package org.anthony.bs.exception;

import org.anthony.bs.utils.SpringContextUtils;

import java.util.Locale;

/**
 * @author Anthony
 */
public class BsException extends Exception {

    private String errCode;

    private Throwable exception;

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    private BsException(String errCode) {
        this.errCode = errCode;
    }

    private BsException(String errCode, Throwable exception) {
        super(exception);
        this.errCode = errCode;
        this.exception = exception;
    }

    public static BsException of(String errCode) {
        return new BsException(errCode);
    }

    public static BsException of(String errCode, Throwable exception) {
        return new BsException(errCode, exception);
    }

    @Override
    public String getMessage() {
        return exception == null ? SpringContextUtils.getApplicationContext().getMessage(errCode, null, Locale.getDefault()) : exception.getMessage();
    }
}
