package com.murava.bankscaling.repository;

import com.murava.bankscaling.dto.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

/**
 * Because we can have thousands of users we need to use Pageable.
 * <p>
 */
public interface UserDao extends JpaRepository<User, Long> {

    @Query("SELECT p FROM User p WHERE p.date_of_birth > ?1 and p.name like ?2")
    Page<User> findAllUsers(Date dateOfBirth, String name, Long phone, String email, Pageable pageable);

}