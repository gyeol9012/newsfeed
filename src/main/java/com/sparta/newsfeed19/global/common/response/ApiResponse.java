package com.sparta.newsfeed19.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse {
    private int code;
    private String message;
    private Object data;
}