package com.security.loginWithoutAdapter.controller;

import com.security.loginWithoutAdapter.dto.LoginRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/login")
    @ApiOperation(value="로그인")
    public String login(@RequestBody LoginRequestDto loginRequest) {
        String encode = bCryptPasswordEncoder.encode(loginRequest.getDbPassword()); // 암호화된 비밀번호
        boolean matches = bCryptPasswordEncoder.matches(loginRequest.getPassword(), encode);
        log.info("결과 값 비교 = {}", matches);

        return String.valueOf(matches);
    }
}
