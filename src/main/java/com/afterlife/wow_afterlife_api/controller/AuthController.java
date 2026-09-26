package com.afterlife.wow_afterlife_api.controller;

import com.afterlife.wow_afterlife_api.dto.RegisterRequest;
import com.afterlife.wow_afterlife_api.dto.RegisterResponse;
import com.afterlife.wow_afterlife_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AccountService service;

    public AuthController(AccountService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test() {
        return "API FUNCIONA";
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) {

        service.register(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new RegisterResponse(
                        true,
                        "Cuenta creada correctamente"
                ));
    }
}
