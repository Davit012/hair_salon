package com.example.hairsalonrest.endpoint;


import com.example.hairsalonrest.dto.userdtos.UserAuthDto;
import com.example.hairsalonrest.dto.userdtos.UserAuthResponseDto;
import com.example.hairsalonrest.dto.userdtos.UserCreateDto;
import com.example.hairsalonrest.dto.userdtos.UserDto;
import com.example.hairsalonrest.security.CurrentUser;
import com.example.hairsalonrest.service.UserService;
import com.example.hairsalonrest.util.JwtTokenUtil;
import com.hairsaloncommon.model.User;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/auth")
    public ResponseEntity<UserAuthResponseDto> auth(@RequestBody UserAuthDto userAuthDto) {
        UserAuthResponseDto byEmail = null;
        Optional<User> checkPassword = userService.findByEmail(userAuthDto.getEmail());
        if (passwordEncoder.matches(userAuthDto.getPassword(), checkPassword.get().getPassword())) {
            byEmail = userService.findByEmail(userAuthDto);
            System.out.println(byEmail.getToken());
        }
        if (byEmail != null) {
            return ResponseEntity.ok(byEmail);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> users() {
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
        Optional<User> byId = userService.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity.ok(mapper.map(byId.get(), UserDto.class));
    }

    @PostMapping
    public ResponseEntity<UserDto> user(@RequestBody UserCreateDto user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDto userCheck = mapper.map(userService.save(mapper.map(user, User.class)), UserDto.class);
        if (userCheck != null){
            return ResponseEntity.ok(userCheck);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> user(@PathVariable(name = "id") int id, @RequestBody UserCreateDto user) {
        if (userService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        User userFromDb = userService.editUser(id, mapper.map(user, User.class));
        return ResponseEntity.ok(mapper.map(userFromDb, UserDto.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        if (userService.findById(id).isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/active")
    public ResponseEntity<UserDto> activeUser(@AuthenticationPrincipal CurrentUser currentUser, @RequestParam("text") String text) {
        System.out.println(currentUser.getUser().getActiveCode());
        System.out.println(currentUser.getUser().getName());
        System.out.println(text);
        User user = userService.verifyEmail(text, currentUser);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

}

