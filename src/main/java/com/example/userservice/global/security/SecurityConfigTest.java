package com.example.userservice.global.security;


import com.example.userservice.domain.auth.jwt.JwtAuthenticationFilter;
import com.example.userservice.domain.auth.jwt.JwtAuthorizationFilter;
import com.example.userservice.domain.auth.jwt.JwtProvider;
import com.example.userservice.domain.auth.oauth.CustomOAuth2MemberService;
import com.example.userservice.domain.auth.oauth.OAuth2LoginFailureHandler;
import com.example.userservice.domain.auth.oauth.OAuth2LoginSuccessHandler;
import com.example.userservice.domain.auth.service.RefreshTokenService;
import com.example.userservice.domain.member.service.MemberService;
import com.example.userservice.global.exception.GlobalExceptionHandlerFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
@Profile("test")
public class SecurityConfigTest {
    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final GlobalExceptionHandlerFilter globalExceptionHandlerFilter;


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors().configurationSource(corsConfigurationSource()) // CORS 필터 우선
                .and()
                .csrf().disable() // CSRF 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .exceptionHandling()
                .and()
                .addFilterBefore(new CorsFilter(corsConfigurationSource()), JwtAuthenticationFilter.class)
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), refreshTokenService, jwtProvider))
                .addFilterBefore(new JwtAuthorizationFilter(authenticationManager(), memberService, jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() // OPTIONS 요청 허용
                .antMatchers(HttpMethod.POST, "/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**").permitAll()
                .antMatchers(HttpMethod.PUT, "/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/**").permitAll()
                .anyRequest().authenticated();

        httpSecurity.addFilterAfter(globalExceptionHandlerFilter, LogoutFilter.class);
        return httpSecurity.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        Logger logger = LoggerFactory.getLogger(SecurityConfigTest.class);
        CorsConfiguration configuration = new CorsConfiguration();

        // 모든 도메인을 허용
        configuration.setAllowedOriginPatterns(List.of("*")); // 또는 setAllowedOrigins(List.of("*"))
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        logger.info("CORS configuration initialized:");
        logger.info("Allowed Origins: {}", configuration.getAllowedOriginPatterns());
        logger.info("Allowed Methods: {}", configuration.getAllowedMethods());
        logger.info("Allowed Headers: {}", configuration.getAllowedHeaders());
        logger.info("Allow Credentials: {}", configuration.getAllowCredentials());
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}