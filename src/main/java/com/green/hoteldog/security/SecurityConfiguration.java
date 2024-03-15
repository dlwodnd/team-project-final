package com.green.hoteldog.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        //세션의 단점 : 메모리에 부담이 크다.
        return httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(http -> http.disable())
                .formLogin(login -> login.disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(
                                        "/api/hotel/mark"
                                        , "/api/hotel/like"
                                        , "/api/user/info"
                                        , "/api/signout"
                                        , "/api/refresh-token"
                                        , "/api/review/fav"
                                        , "/api/dog/**"
                                        , "/api/board/my-comment"
                                        , "/api/board/my-board").authenticated()
                                .requestMatchers("/api/reservation/**").hasAnyRole("USER")
                                .requestMatchers(HttpMethod.POST
                                        , "/api/review"
                                        , "/api/user/follow"
                                        , "/api/board"
                                        , "/api/board/comment").authenticated()
                                .requestMatchers(HttpMethod.GET
                                        , "/api/user").authenticated()
                                .requestMatchers(HttpMethod.DELETE
                                        , "/api/review"
                                        , "/api/board"
                                        , "/api/board/comment").authenticated()
                                .requestMatchers(HttpMethod.PATCH
                                        , "/api/review"
                                        , "/api/board/comment").authenticated()
                                .requestMatchers(HttpMethod.PUT
                                        , "/api/review"
                                        , "/api/board").authenticated()
                                .requestMatchers(
                                        "/api/manager"
                                        ,"/api/manager/**").hasRole("ADMIN")
                                .requestMatchers(
                                        "/api/business/**"
                                        ,"/api/business").hasAnyRole("BUSINESS_USER")
                                .anyRequest().permitAll()

                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(except -> {
                    except.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                            .accessDeniedHandler(new JwtAccessDeniedHandler());
                }).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
