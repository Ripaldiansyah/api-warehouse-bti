package id.bti.warehouse.service;

import java.util.List;

import org.springframework.stereotype.Service;

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

}
