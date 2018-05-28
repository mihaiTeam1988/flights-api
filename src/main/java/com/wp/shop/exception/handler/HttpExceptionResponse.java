package com.wp.shop.exception.handler;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Getter
@Builder
public class HttpExceptionResponse {

    private int status;
    private String message;
    private Collection<String> errors;
}
