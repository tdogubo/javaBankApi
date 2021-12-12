package com.etz.bankapi.domain.response;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ErrorResponse {
        private Boolean status = false;
        private Object data;

}
