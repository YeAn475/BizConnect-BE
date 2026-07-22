package com.springboot.bizconnect.config;

import com.springboot.bizconnect.domain.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    /*
        SecurityFilterChain : 모든 HTTP 요청이 거쳐가는 보안 필터 체인(이 요청은 통과시켜, 막아 이런 규칙을 정의한 값은 리턴함)
        HttpSecurity : 보안 설정을 도와주는 도구(이걸로 규칙을 설정함)

        csrf(Cross-Site Request Forgery(사이트 간 요청 위조)) : 다른 사이트에서 우리 서버에 몰래 요청 보내는 공격
        disable로 막은 이유는 JWT는 토큰 기반이라 CSRF 공격에 안전해서 비활성화 해도 괜찮음

        세션 정책 : 서버가 기억하는 방식(해당 사용자가 로그인을 했는지 유무 등)
        sessionCreationPolicy : 세션을 어떻게 관리할 것인가? 정책 설정 토큰 인증하는 함수가 아닌 세션 생성 규칙을 정하는 함수
        종류 : ALWAYS(항상 세션 생성), IF_REQUIRED(필요할 때만 세션 생성(Default)), NEVER(있으면 쓰긴 함), STATELESS(세션 사용 아에 안함)
        STATELESS : JWT 방식은 토큰에 정보가 다 들어가 있어서 서버가 기억할 필요가 없음, 그래서 매번 요청할 때 마다 토큰으로 인증
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // .csrf(csrf -> csrf.disable()) 람다식 표현으로 수정
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/auth/**").permitAll();
                    auth.requestMatchers("/api/user/**").authenticated();
                    auth.requestMatchers("/api/alarm/**").authenticated();
                    auth.requestMatchers("/api/user/signup").permitAll();
                    auth.requestMatchers("/api/user/password/reset").permitAll();
                    auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/ws/**").permitAll();
                    auth.anyRequest().authenticated();
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
