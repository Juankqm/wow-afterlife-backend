package com.afterlife.wow_afterlife_api.service;

import com.afterlife.wow_afterlife_api.dto.RegisterRequest;
import com.afterlife.wow_afterlife_api.entity.Account;
import com.afterlife.wow_afterlife_api.exception.AccountAlreadyExistsException;
import com.afterlife.wow_afterlife_api.repository.AccountRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public void register(RegisterRequest request) {

        String username = request.username()
                .trim()
                .toUpperCase();

        String password = request.password();

        String email = request.email()
                .trim()
                .toLowerCase();

        if (repository.findByUsername(username).isPresent()) {

            throw new AccountAlreadyExistsException(
                    "La cuenta ya existe"
            );
        }

        AzerothCoreSRP6.RegistrationData registrationData =
                AzerothCoreSRP6.makeRegistrationData(
                        username,
                        password
                );

        Account account = new Account();

        account.setUsername(username);

        account.setSalt(
                registrationData.salt()
        );

        account.setVerifier(
                registrationData.verifier()
        );

        account.setEmail(email);

        account.setRegMail(email);

        account.setExpansion(2);

        repository.save(account);
    }
}
