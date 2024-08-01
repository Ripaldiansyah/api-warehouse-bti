package id.bti.warehouse.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.bti.warehouse.dto.request.UserRequest;
import id.bti.warehouse.dto.response.UserResponse;
import id.bti.warehouse.entity.User;
import id.bti.warehouse.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUser() {
        List<UserResponse> allUser = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(allUser);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserRequest request) {
        UserResponse response;
        try {
            response = userService.saveUser(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @PutMapping
    public ResponseEntity<?> editUser(@RequestBody UserRequest request) {
        UserResponse response;
        try {
            response = userService.editUser(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

}
