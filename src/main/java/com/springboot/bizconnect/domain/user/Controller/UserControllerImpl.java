package com.springboot.bizconnect.domain.user.Controller;


import com.springboot.bizconnect.domain.user.dto.Sign.SignupRequestDto;
import com.springboot.bizconnect.domain.user.dto.Sign.SignupResponseDto;
import com.springboot.bizconnect.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService userService;

    @Override
    public ResponseEntity<SignupResponseDto> addUser(@RequestBody @Valid SignupRequestDto requestDto) {
        SignupResponseDto responseDto = userService.addUser(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
