package com.example.hairsalonrest.util;

import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import com.hairsaloncommon.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateAdmin {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void createAdmin() {
        if (userRepository.findByEmail("admin").isEmpty()) {

            userRepository.save(User.builder()
                    .email("admin")
                    .surname("admin")
                    .name("admin")
                    .password(passwordEncoder.encode("admin"))
                    .userType(UserType.valueOf("ADMIN"))
                    .isEmailVerified(true)
                    .build());
        }
    }
}
