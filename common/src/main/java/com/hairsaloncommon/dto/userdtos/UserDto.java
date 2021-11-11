package com.hairsaloncommon.dto.userdtos;

import com.hairsaloncommon.model.Gender;
import com.hairsaloncommon.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserDto {
    private int id;
    private String name;
    private String surname;
    private Gender gender;
    private String email;
    private UserType userType;
}
