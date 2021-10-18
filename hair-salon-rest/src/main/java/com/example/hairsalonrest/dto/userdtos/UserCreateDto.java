package com.example.hairsalonrest.dto.userdtos;

import com.hairsaloncommon.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserCreateDto {
    private String name;
    private String surname;
    private Gender gender;
    private String email;
    private String password;
}
