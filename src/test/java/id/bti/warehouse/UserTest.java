package id.bti.warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import id.bti.warehouse.constant.UserConstant;
import id.bti.warehouse.dto.request.UserRequest;
import id.bti.warehouse.dto.response.UserResponse;
import id.bti.warehouse.entity.User;
import id.bti.warehouse.repository.UserRepository;
import id.bti.warehouse.service.UserService;

@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void testAddUser() {
        UserRequest user = new UserRequest(
                null,
                "Ripaldiansyah",
                "Ripal@mail.com",
                UserConstant.ROLE_ADMIN);

        User userEntity = new User(
                null,
                "Ripaldiansyah",
                "Ripal@mail.com",
                UserConstant.ROLE_ADMIN,
                null);

        when(userRepository.save(any(User.class))).thenReturn(userEntity);

        UserResponse saveUser = userService.saveUser(user);
        assertEquals("Ripaldiansyah", saveUser.getFullName());
        assertEquals("ADMIN", saveUser.getRole());
        assertEquals("Ripal@mail.com", saveUser.getEmail());
    }

    @Test
    public void testAddUserWithInvalidEmail() {
        UserRequest user = new UserRequest(
                null,
                "Ripaldiansyah",
                "ripal.mail.com",
                UserConstant.ROLE_ADMIN);

        when(userRepository.save(any(User.class))).thenThrow(new RuntimeException("Email tidak valid"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals("Email tidak valid", exception.getMessage());
    }

    @Test
    public void testAddUserWithDuplicateEmail() {
        UserRequest user = new UserRequest(
                null,
                "Ripaldiansyah",
                "Ripal@mail.com",
                UserConstant.ROLE_ADMIN);

        when(userRepository.findByEmail("Ripal@mail.com")).thenReturn(Optional.of(new User(
                null,
                "Ripaldiansyah",
                "Ripal@mail.com",
                UserConstant.ROLE_ADMIN,
                null)));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.saveUser(user);
        });

        assertEquals("Email telah terdaftar", exception.getMessage());
    }
}
