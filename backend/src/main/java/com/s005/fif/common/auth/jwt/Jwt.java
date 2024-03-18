package com.s005.fif.common.auth.jwt;

import lombok.Builder;

public record Jwt(String accessToken, String refreshToken) {
    @Builder
    public Jwt {
    }
}
