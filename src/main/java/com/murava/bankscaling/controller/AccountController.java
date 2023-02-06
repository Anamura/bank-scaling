package com.murava.bankscaling.controller;

import com.murava.bankscaling.dto.Account;
import com.murava.bankscaling.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{accountId}")
    public Account getAccount(@PathVariable("accountId") Long accountId) {
        Account account = accountService.getById(accountId);
        return account;
    }

    @PutMapping("/transfer/{accountId}")
    public void transferBalance(@PathVariable Long accountId, @RequestParam Double value, @AuthenticationPrincipal Jwt token) {
        Long from = (Long) token.getClaims().get("user_id");
        if (value <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Value should be positive");
        }
        accountService.transferBalance(from, accountId, value);
    }

    @DeleteMapping(path = "/delete/{accountId}")
    public void deleteAccount(@PathVariable("accountId") Long accountId) {
        accountService.delete(accountId);
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

}
