package com.etz.bankapi.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiResponse {
    private Long userId;
    private Long id;
    private String title;
    private String completed;
}
