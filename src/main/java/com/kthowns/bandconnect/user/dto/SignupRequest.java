package com.kthowns.bandconnect.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "사용자 계정은 필수입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4~20 사이여야 합니다.")
    private String username;

    @NotBlank(message = "실명은 필수입니다.")
    private String name;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Size(min = 8, max = 32)
    private String password;
}
