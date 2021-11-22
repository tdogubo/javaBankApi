package com.etz.bank_api.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Entity
@Data
@DiscriminatorValue("CURRENT")
public class CurrentAccountModel extends AccountModel {
}


