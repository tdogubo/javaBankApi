package com.etz.bankapi.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Banker extends User {
    @ManyToMany
    @JoinColumn(name = "client_id")
    private List<Client> client;

}