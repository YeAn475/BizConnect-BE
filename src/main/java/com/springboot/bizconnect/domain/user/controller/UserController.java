package com.springboot.bizconnect.domain.user.controller;

import com.springboot.bizconnect.domain.user.dto.sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.sign.SignupResponseDto;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

public interface UserController {
    // 회원가입
    ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequestDto requestDto);

}
