package com.etz.bankapi.repository;

import com.etz.bankapi.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {


}
