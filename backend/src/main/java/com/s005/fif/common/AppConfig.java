package com.s005.fif.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class AppConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("*"); // FIXME: 웹 패널
//        config.addAllowedOrigin("*"); // FIXME: 안드로이드
//        config.addAllowedOrigin("*"); // FIXME: 파이썬 서버
        config.addAllowedOrigin("*"); // FIXME: 개발용, 오리진 모두 허용
        config.addAllowedHeader("*"); // header
        config.addAllowedMethod("*"); // method
//        config.setAllowCredentials(true);
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
