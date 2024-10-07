package com.shop31cm.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    /**
     * user error code
     * 1xxxx
     */
    ALREADY_EXISTS_NICKNAME(10000, "nickname already exists", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS_EMAIL(10001, "email already exists", HttpStatus.BAD_REQUEST),
    ALREADY_EXISTS_PHONE(10002, "phone already exists", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD_FORMAT(10003, "Password format is invalid.", HttpStatus.BAD_REQUEST),
    CONFIRM_PASSWORD_NOT_MATCH(10004, "password and Confirmation password do not match.", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_FOUND(10005, "password not found", HttpStatus.BAD_REQUEST),
    INVALID_AUTHORIZATION(10006, "Invalid or missing Authorization", HttpStatus.BAD_REQUEST),
    INCORRECT_PASSWORD(10007, "The password is incorrect", HttpStatus.BAD_REQUEST),
    NOT_FOUND_USER(10008, "User could not be found", HttpStatus.BAD_REQUEST),
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
