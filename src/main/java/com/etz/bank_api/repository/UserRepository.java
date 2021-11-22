package com.etz.bank_api.repository;

import com.etz.bank_api.model.UserModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long>{

    
}
