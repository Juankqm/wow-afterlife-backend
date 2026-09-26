package com.afterlife.wow_afterlife_api.service;

import com.afterlife.wow_afterlife_api.dto.RegisterRequest;
import com.afterlife.wow_afterlife_api.entity.Account;
import com.afterlife.wow_afterlife_api.repository.AccountRepository;
import com.afterlife.wow_afterlife_api.service.AzerothCoreSRP6;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    public void register(RegisterRequest request) {

        String username =
                request.username()
                        .trim()
                        .toUpperCase();

        String password =
                request.password();

        String email =
                request.email()
                        .trim()
                        .toLowerCase();

        /*
         * Comprobar si la cuenta existe.
         */
        if (repository.findByUsername(username).isPresent()) {

            throw new RuntimeException(
                    "La cuenta ya existe"
            );
        }

        /*
         * Generar salt + verifier compatibles
         * con AzerothCore.
         */
        AzerothCoreSRP6.RegistrationData registrationData =
                AzerothCoreSRP6.makeRegistrationData(
                        username,
                        password
                );

        /*
         * Crear entidad.
         */
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

        /*
         * 2 = Wrath of the Lich King.
         */
        account.setExpansion(2);

        /*
         * Guardar.
         */
        repository.save(account);
    }
}
