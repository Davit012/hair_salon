package com.example.hairsalonrest.dto.userdtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserAuthDto {
    private String email;
    private String password;
}
