package com.s005.fif.common.jwt;

import lombok.Builder;
import lombok.Getter;

public record Jwt(String accessToken, String refreshToken) {
    @Builder
    public Jwt {
    }
}
