package com.etz.bank_api.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.transform.sax.SAXResult;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {
    private Long userId;
    private Long accountNumber;
    private Double amount;
    private String depositType;


}
