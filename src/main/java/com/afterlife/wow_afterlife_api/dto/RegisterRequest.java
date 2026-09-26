package com.afterlife.wow_afterlife_api.dto;

public record RegisterRequest(
        String username,
        String password,
        String email
) {
}
