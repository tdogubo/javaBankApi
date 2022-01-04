package com.etz.bankapi.domain.response;

import lombok.Getter;


@Getter
public class AppResponse<T> {
    private final boolean status;
    private T data;
    private String message;

    public AppResponse(final boolean status, final String message) {
        this.status = status;
        this.message = message;
    }

    public AppResponse(final boolean status, final T data) {
        this.status = status;
        this.data = data;
    }

}
