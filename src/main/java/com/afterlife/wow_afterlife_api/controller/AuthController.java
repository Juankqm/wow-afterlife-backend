package com.afterlife.wow_afterlife_api.controller;

import com.afterlife.wow_afterlife_api.entity.Account;
import com.afterlife.wow_afterlife_api.repository.AccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthController {

    private final AccountRepository repository;

    public AuthController(AccountRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/accounts")
    public List<Account> getAccounts() {
        return repository.findAll();
    }
}