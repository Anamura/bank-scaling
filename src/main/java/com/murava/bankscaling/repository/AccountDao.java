package com.murava.bankscaling.repository;

import com.murava.bankscaling.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDao extends JpaRepository<Account, Long> {
}