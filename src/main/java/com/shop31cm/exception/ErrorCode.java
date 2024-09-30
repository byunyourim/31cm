package com.shop31cm.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    /**
     * user error code
     * 1xxxx
     */
    ALREADY_EXISTS_NICKNAME(10000, "nickname already exists", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS_EMAIL(10001, "email already exists", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT(10002, "Password format is invalid.", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_NOT_MATCH(10003, "password and Confirmation password do not match.", HttpStatus.BAD_REQUEST),
    ;

    public final int code;
    public final String message;
    public final HttpStatus httpcode;

    ErrorCode(int code, final String message, HttpStatus httpcode) {
        this.code = code;
        this.message = message;
        this.httpcode = httpcode;

    }

}
