package hu.flowacademy.netbank.service;

import hu.flowacademy.netbank.exception.ValidationException;
import hu.flowacademy.netbank.model.Role;
import hu.flowacademy.netbank.model.User;
import hu.flowacademy.netbank.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void testUserSaveFail() {
        assertThrows(ValidationException.class, () -> userService.save(
                givenEmptyUser()
        ));
        assertThrows(ValidationException.class, () -> userService.save(
                givenUserWithEmail()
        ));
        assertThrows(ValidationException.class, () -> userService.save(
                givenUserWithEmailAndPassword()
        ));
    }

    @Test
    public void testUserSaveSuccess() {
        String userId = UUID.randomUUID().toString();
        User user = User.builder()
                .email("asd@bsd.com")
                .fullName("asd")
                .password("123")
                .build();

        when(userRepository.save(any())).thenReturn(user.toBuilder()
                .id(userId)
                .role(Role.USER)
                .build());


        userService.save(user);

        verify(userRepository).save(any());
    }

    @Test
    public void testUserUpdateSuccess() {
        String userId = UUID.randomUUID().toString();
        User user = User.builder()
                .email("asd@bsd.com")
                .fullName("asd")
                .password("123")
                .build();

        when(userRepository.save(any())).thenReturn(user.toBuilder()
                .id(userId)
                .role(Role.USER)
                .build());


        userService.update(userId, user);

        verify(userRepository).save(any());
    }

    private User givenUserWithEmail() {
        return User.builder().email("asd@bsd.com").build();
    }

    private User givenUserWithEmailAndPassword() {
        return User.builder().email("asd@bsd.com").password("123").build();
    }

    private User givenEmptyUser() {
        return User.builder().build();
    }

}