package com.murava.bankscaling.repository;

import com.murava.bankscaling.dto.EmailData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailDataDao extends JpaRepository<EmailData, Long> {
}