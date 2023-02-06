package com.murava.bankscaling.repository;

import com.murava.bankscaling.dto.PhoneData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneDataDao extends JpaRepository<PhoneData, Long> {
}