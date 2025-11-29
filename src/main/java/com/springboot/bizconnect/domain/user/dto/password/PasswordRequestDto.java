package com.springboot.bizconnect.domain.user.dto.password;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRequestDto {
    @NotBlank(message = "기존 비밀번호를 입력하세요.")
    private String password;
    @NotBlank(message = "새 비밀번호를 입력하세요.")
    private String newPassword;
    @NotBlank(message = "다시한번 입력해주세요")
    private String confirmPassword;
}
