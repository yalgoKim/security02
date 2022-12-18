package com.security.loginWithoutAdapter.config;

import com.security.loginWithoutAdapter.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() // 권한 부여에 따른 접근 설정
                .antMatchers(
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/file/**",
                        "/image/**",
                        "/swagger/**",
                        "/swagger-ui/**",
                        "/h2-console/**",
                        "/favicon.ico",
                        "/h2/**"
                ).permitAll()
                // login 없이 접근 허용 하는 url : 로그인, 회원가입, 유저화면은 누구나 접근 가능함 // permitAll() : 누구나 접근이 가능
                .antMatchers("/auth/**").permitAll()
                // '/admin'의 경우 ADMIN 권한이 있는 사용자만 접근이 가능 // hasRole() : 특정 권한이 있는 사람만 접근 가능
                .antMatchers("/admin").hasRole("ADMIN")
                // 나머지 요청은 권한의 종류 상관 없이 무조건 권한 있어야 접근 가능 // authenticated() : 권한이 있으면 무조건 접근 가능 // anyRequest() : antMatchers에서 설정하지 않은 나머지 경로
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        http.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/");
    }
}
