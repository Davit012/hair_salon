package com.example.hairsalonrest.endpoint;


import com.example.hairsalonrest.security.CurrentUser;
import com.example.hairsalonrest.util.CreateAdmin;
import com.hairsaloncommon.dto.userdtos.*;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {

    private final UserService userService;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final CreateAdmin createAdmin;


    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthDto userAuthDto) {
        createAdmin.createAdmin();
        UserAuthResponseDto byEmail = null;
        Optional<User> checkPassword = userService.findUserByEmail(userAuthDto.getEmail());

        if (checkPassword.isPresent() && passwordEncoder.matches(userAuthDto.getPassword(), checkPassword.get().getPassword())) {
            byEmail = userService.findUserByEmail(userAuthDto);
        }

        if (byEmail != null) {
            return ResponseEntity.ok(byEmail);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> all = userService.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        if (!all.isEmpty()) {
            for (User user : all) {
                UserDto userDto = mapper.map(user, UserDto.class);
                userDtos.add(userDto);
            }
            return ResponseEntity.ok(userDtos);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable("id") int id) {
        Optional<User> byId = userService.findUserById(id);
        if (byId.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(mapper.map(byId.get(), UserDto.class));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid UserCreateDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userCheck = userService.save(mapper.map(user, User.class));
        if (userCheck != null) {
            return ResponseEntity.ok(mapper.map(userCheck, UserDto.class));
        }
        return ResponseEntity.badRequest().body("User with email " + user.getEmail() +
                " already is present, we can reset your password if you forgot it");
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(name = "id") int id, @RequestBody @Valid UserCreateDto user) {
        if (userService.findUserById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User userFromDb = userService.editUser(id, mapper.map(user, User.class));
        return ResponseEntity.ok(mapper.map(userFromDb, UserDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (userService.findUserById(id).isPresent()) {
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/email")
    public ResponseEntity<?> sendTokenForReset(HttpServletRequest httpServletRequest) {
        String email = httpServletRequest.getHeader("mail");
        if (email == null) {
            return ResponseEntity.badRequest().body("Please Input your email");
        }
        User user = userService.sendToken(email);
        if (user != null) {
            return ResponseEntity.ok("Your token was sent to your email");
        }
        return ResponseEntity.badRequest().body("Your email does not verified");
    }

    @PatchMapping
    public ResponseEntity<?> resetPassword(HttpServletRequest httpServletRequest, @RequestBody UserResetPasswordDto userPassword) {
        String email = httpServletRequest.getHeader("mail");
        Optional<User> userByEmail = userService.findUserByEmail(email);
        if (userByEmail.isPresent() &&
                userPassword.getRepeatNewPassword().equals(userPassword.getNewPassword())) {
            User user = userByEmail.get();
            User updatedUser = userService.resetPassword(user, userPassword);
            if (updatedUser != null) {
                return ResponseEntity.ok("Your password is restored");
            }
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/active")
    public ResponseEntity<?> activeUser(@AuthenticationPrincipal CurrentUser currentUser,
                                        @RequestParam("text") String text) {
        User user = userService.verifyEmail(text, currentUser.getUser());
        if (user == null) {
            return ResponseEntity.badRequest().body("please log in for active your account");
        }
        return ResponseEntity.noContent().build();
    }

}

