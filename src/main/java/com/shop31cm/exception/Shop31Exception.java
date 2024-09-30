package com.shop31cm.exception;

import java.util.Map;
import java.util.function.Consumer;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class Shop31Exception extends RuntimeException {

    private final Integer code;
    private final String message;
    private final HttpStatus httpcode;
    private final Map<String, Object> parameters;
    private final Consumer<String> logger;

    public Shop31Exception(ErrorCode errorCode) {
        this(errorCode, null, null);
    }

    public Shop31Exception(ErrorCode errorCode,Map<String, Object> parameters, Consumer<String> logger) {
        this.code = errorCode.code;
        this.message = errorCode.message;
        this.httpcode = errorCode.httpcode;
        this.parameters = parameters;
        this.logger = logger;
    }

}
