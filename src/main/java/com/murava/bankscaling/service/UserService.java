package com.murava.bankscaling.service;

import com.murava.bankscaling.dto.EmailData;
import com.murava.bankscaling.repository.EmailDataDao;
import com.murava.bankscaling.repository.UserDao;
import com.murava.bankscaling.dto.User;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserDao userDao;

    @Autowired
    private EmailDataDao emailDao;

    @Autowired
    EntityManager entityManager;

    public User registerNewUser(User user) {
        return userDao.save(user);
    }

    public User getById(Long id) {
        Optional<User> user = userDao.findById(id);
        return user.get();
    }

    public User updateUser(Long userId, User user) {

        if (user.getId() != userId) {
            throw new AccessDeniedException("Couldn't modify another User member=" + user.getId());
        }
        return userDao.findById(userId)
                .map(usr -> {
                    usr.setEmail(user.getEmail());
                    usr.setPhone(user.getPhone());
                    return userDao.save(usr);
                })
                .orElseGet(() -> {
                    user.setId(userId);
                    return userDao.save(user);
                });
    }

    @Cacheable("users")
    public Page<User> findAllPageable(Date dateOfBirth, String name, Long phone,
                                      String email, PageRequest of) {
        log.info("Getting users not from cache...");

        if (dateOfBirth != null) {
            return userDao.findAllUsers(dateOfBirth, name, phone, email, of);
        }
        return new PageImpl<>(Collections.emptyList());
    }


    public EmailData addEmail(Long userId, EmailData email, Long accountId) {

        Optional<User> user = userDao.findById(userId);
        if (user.isPresent()) {
            email.setUser(user.get());
        }
        log.info("Update if email doesn't exist for other members...");
        return emailDao.save(email);
    }

}
