package com.grupo1.domain.aggregates.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseBase {
    private int code;
    private String message;
    private Object data;
}
