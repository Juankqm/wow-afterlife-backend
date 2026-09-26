package com.afterlife.wow_afterlife_api.controller;

import com.afterlife.wow_afterlife_api.dto.RegisterRequest;
import com.afterlife.wow_afterlife_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AccountService service;

    public AuthController(AccountService service) {
        this.service = service;
    }


    @PostMapping("/register")
    public String register(
            @Valid @RequestBody RegisterRequest request
    ) {

        service.register(request);

        return "OK";
    }
}
