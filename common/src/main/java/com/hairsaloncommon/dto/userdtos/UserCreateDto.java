package com.hairsaloncommon.dto.userdtos;

import com.hairsaloncommon.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserCreateDto {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Surname is required")
    private String surname;
    @NotNull
    private Gender gender;
    @Email
    @NotEmpty(message = "email is required")
    private String email;
    @Size(min = 6, max = 20)
    private String password;
}
