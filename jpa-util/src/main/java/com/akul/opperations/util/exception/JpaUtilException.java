package com.akul.opperations.util.exception;

public class JpaUtilException extends RuntimeException {
    public JpaUtilException(String msg, Exception ex) {
        super(msg, ex);
    }
}
