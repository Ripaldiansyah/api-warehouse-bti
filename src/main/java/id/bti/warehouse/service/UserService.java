package id.bti.warehouse.service;

import java.util.ArrayList;
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

    public List<UserResponse> getAllUser() {
        List<User> users = userRepository.findAll();

        List<UserResponse> tempUsers = new ArrayList<>();

        for (User user : users) {
            UserResponse response = new UserResponse(
                    user.getId(), user.getFullName(), user.getEmail(), user.getRole());

            tempUsers.add(response);
        }

        return tempUsers;
    }

    public List<User> detailUser() {
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

    public UserResponse editUser(UserRequest request) {
        Optional<User> userOpt = userRepository.findById(request.getId());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("ID Tidak ditemukan");
        }

        Optional<User> emailOpt = userRepository.findByEmail(request.getEmail());

        if (!emailOpt.isEmpty() && !userOpt.get().getEmail().equalsIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Email telah terdaftar");
        }

        if (!request.getEmail().contains("@")) {
            throw new RuntimeException("Email tidak valid");
        }

        User user = new User(
                request.getId(),
                request.getFullName(),
                request.getEmail(),
                request.getRole(),
                null);

        user = userRepository.save(user);

        UserResponse response = new UserResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRole());

        return response;
    }

    public void deleteById(String id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new RuntimeException("User tidak ditemukan");
        }

        userRepository.deleteById(id);
    }
}
