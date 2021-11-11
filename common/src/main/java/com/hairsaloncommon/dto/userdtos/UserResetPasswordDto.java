package com.hairsaloncommon.dto.userdtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResetPasswordDto {
    private String password;
    private String newPassword;
    private String repeatNewPassword;
    private String token;
}
