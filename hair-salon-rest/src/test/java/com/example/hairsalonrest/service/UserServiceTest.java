package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.example.hairsalonrest.dto.userdtos.UserResetPasswordDto;
import com.example.hairsalonrest.repository.UserRepository;
import com.example.hairsalonrest.security.CurrentUser;
import com.hairsaloncommon.model.User;
import com.hairsaloncommon.model.UserType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
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

        when(userRepository.save(Mockito.any())).thenReturn(user);
        List<User> users = userService.findAll();
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void saveUserTest() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        when(userRepository.save(Mockito.any())).thenReturn(user);
        User save = userService.save(user);

        assertThat(save.getName()).isEqualTo(user.getName());
    }

    @Test
    public void findUserById() {
        int id = 4;
        User user = User.builder()
                .id(id)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        when(userRepository.save(Mockito.any())).thenReturn(user);
        User save = userService.save(user);
        Optional<User> found = userService.findUserById(id);
        assertEquals(found.get().getId(), save.getId());
    }

    @Test
    public void findUserByEmail() {
        String email = "test@gmail.com";
        User user = User.builder()
                .id(4)
                .name("test")
                .email(email)
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        when(userRepository.save(Mockito.any())).thenReturn(user);
        User save = userService.save(user);
        Optional<User> found = userService.findUserByEmail(email);
        assertEquals(found.get().getEmail(), save.getEmail());
    }

    @Test
    public void editExistUserTest() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        when(userRepository.save(Mockito.any())).thenReturn(user);
        User save = userService.save(user);
        save.setName("RealName");
        User editUser = userService.editUser(user.getId(), user);
        assertEquals(editUser.getName(), is("RealName"));
    }

    @Test
    public void deleteUserTest() {
        int id = 4;
        User user = User.builder()
                .id(id)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        when(userRepository.save(Mockito.any())).thenReturn(user);
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

        when(userRepository.save(Mockito.any())).thenReturn(user);
        User resetPassword = userService.resetPassword(user, UserResetPasswordDto.builder().build());
        assertThat(user.getPassword().equals(resetPassword));
    }

    @Test
    public void sendToken() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.CLIENT)
                .build();

        when(userRepository.save(Mockito.any())).thenReturn(user);
        User sendToken = userService.sendToken(user.getEmail());
        assertThat(sendToken.getEmail().equals(user.getEmail()));
    }

    @Test
    public void createAdmin() {
        User user = User.builder()
                .id(4)
                .name("test")
                .email("test@gmail.com")
                .password("test")
                .userType(UserType.ADMIN)
                .build();

        when(userRepository.save(Mockito.any())).thenReturn(user);
        User save = userRepository.save(user);
        assertThat(save.getUserType().toString().equalsIgnoreCase("admin"));

    }


}