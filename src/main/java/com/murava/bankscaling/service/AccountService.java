package com.murava.bankscaling.service;

import com.murava.bankscaling.repository.AccountDao;
import com.murava.bankscaling.dto.Account;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(BalanceService.class);

    @Autowired
    AccountDao accountDao;

    @Autowired
    EntityManager entityManager;

    public Account getById(Long id) {
        Optional<Account> optionalAccount = accountDao.findById(id);
        if (optionalAccount.isPresent()) {
            return optionalAccount.get();
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        accountDao.deleteById(id);
    }

    public List<Account> getAllAccounts() {
        return this.accountDao.findAll();
    }

    public void transferBalance(Long from, Long to, Double value) {
        log.info("Processing transfer {} from account {} to {}", value, from, to);

        Account fromAccount = entityManager.find(Account.class, from);
        Account toAccount = entityManager.find(Account.class, to);
        if (fromAccount == null) {
            throw new RuntimeException("Account not found" + from);
        }
        if (fromAccount.getBalance() < value) {
            throw new RuntimeException("Balance couldn't be negative");
        }
        entityManager.lock(fromAccount, LockModeType.OPTIMISTIC);
        entityManager.lock(toAccount, LockModeType.OPTIMISTIC);

        fromAccount.setBalance(fromAccount.getBalance() - value);
        accountDao.save(fromAccount);

        double toAccountBalance = toAccount.getBalance();
        toAccount.setBalance(toAccountBalance + value);
        accountDao.save(toAccount);
    }

}
