package com.etz.bank_api.config;

import com.etz.bank_api.domain.response.UserResponse;
import lombok.Getter;


@Getter
public class Response<T>{
    boolean status;
    T data;
    String message;

    public Response(final boolean status, final String message) {
        this.status = status;
        this.message = message;
    }
    public Response(final boolean status, final T data) {
        this.status = status;
        this.data = data;
    }

}
