package com.pranjal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class JwtDecoderConfig {

    @Value("${jwt.secret_key}")
    private String secretKey;
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8),
                "HmacSHA256")).build();
    }
}
