package com.example.bai5_tongvutanphat_2280602321.repository;

import com.example.bai5_tongvutanphat_2280602321.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT a FROM Account a WHERE a.login_name = :loginName")
    Optional<Account> findByLoginName(String loginName);
}
