package com.murava.bankscaling.service;

import com.murava.bankscaling.repository.AccountDao;
import com.murava.bankscaling.dto.Account;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BalanceService {

    private static final Logger log = LoggerFactory.getLogger(BalanceService.class);

    @Autowired
    AccountDao accountDao;

    @Value("${bank-rate}")
    int value;

    @Scheduled(cron = "*/30 * * * * *")
    public void updateBalance() {
        log.info("Processing update balance...");

        List<Account> accountList = accountDao.findAll();

        for (Account account : accountList) {
            double balance = account.getBalance();
            double percent = balance * (value / (100));

            balance = (double) Math.round((balance + percent) * 100) / 100;
            account.setBalance(balance);

            accountDao.save(account);
        }
    }
}
