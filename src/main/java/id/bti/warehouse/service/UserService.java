package id.bti.warehouse.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import id.bti.warehouse.dto.request.UserRequest;
import id.bti.warehouse.dto.response.UserResponse;
import id.bti.warehouse.entity.User;
import id.bti.warehouse.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public UserResponse saveUser(UserRequest userRequest) {
        Optional<User> userOpt = userRepository.findByEmail(userRequest.getEmail());

        if (!userOpt.isEmpty()) {
            throw new RuntimeException("Email telah terdaftar");
        }

        if (!userRequest.getEmail().contains("@")) {
            throw new RuntimeException("Email tidak valid");
        }

        User user = new User(
                null,
                userRequest.getFullName(),
                userRequest.getEmail(),
                userRequest.getRole(),
                null);
        user = userRepository.save(user);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole());

        return response;
    }

}
