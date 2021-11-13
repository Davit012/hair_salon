package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.example.hairsalonrest.security.CurrentUser;
import com.hairsaloncommon.dto.userdtos.UserResetPasswordDto;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import com.hairsaloncommon.repository.UserRepository;
import com.hairsaloncommon.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void findAllUsers() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> all = userService.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void saveUserTest() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.valueOf("CLIENT"))
                .build();
        user.setActiveCode(UUID.randomUUID());
        when(userRepository.save(Mockito.any())).thenReturn(user);
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        assertEquals(1, userRepository.findAll().size());
    }

    @Test
    public void findUserById() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.valueOf("CLIENT"))
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findUserById(user.getId());
        assertTrue(foundUser.isPresent());
        assertEquals(foundUser.get().getId(), user.getId());
    }

    @Test
    public void findUserByEmail() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .activeCode(UUID.randomUUID())
                .token(UUID.randomUUID())
                .userType(UserType.valueOf("CLIENT"))
                .isEmailVerified(true)
                .build();
        when(userService.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Optional<User> found = userService.findUserByEmail(user.getEmail());
        assertTrue(found.isPresent());
        assertEquals(found.get().getEmail(), user.getEmail());
    }

    @Test
    public void editUserTest() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.valueOf("CLIENT"))
                .isEmailVerified(true)
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(Mockito.any())).thenReturn(user);
        User save = userRepository.save(user);
        save.setName("newFirstName");
        User editUser = userService.editUser(user.getId(), save);
        assertEquals(editUser.getName(), "newFirstName");
    }

    @Test
    public void deleteUserTest() {
        int id = 4;
        User user = User.builder()
                .id(id)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.valueOf("CLIENT"))
                .isEmailVerified(true)
                .build();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.deleteUserById(id);
        verify(userRepository).deleteById(id);
    }

    @Test
    public void verifyEmailTest() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        user.setActiveCode(UUID.randomUUID());
        when(userRepository.save(Mockito.any())).thenReturn(user);
        CurrentUser currentUser = new CurrentUser(user);
        assertThat(currentUser.getUser().getActiveCode().equals(user.getActiveCode()));
    }

    @Test
    public void resetPassword() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        UserResetPasswordDto userResetPasswordDto = UserResetPasswordDto.builder()
                .password(user.getPassword())
                .newPassword("newPassword")
                .repeatNewPassword("newPassword")
                .build();
        when(userRepository.save(Mockito.any())).thenReturn(user);
        userService.resetPassword(user, userResetPasswordDto);
        assertThat(user.getPassword().equals("newPassword"));
    }

    @Test
    public void createAdmin() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .isEmailVerified(true)
                .userType(UserType.ADMIN)
                .build();

        when(userRepository.save(Mockito.any())).thenReturn(user);
        User save = userRepository.save(user);
        assertThat(save.getUserType().toString().equalsIgnoreCase("admin"));

    }


}