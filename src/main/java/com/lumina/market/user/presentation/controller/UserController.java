package com.lumina.market.user.presentation.controller;

import com.lumina.market.shared.exception.ApiResponse;
import com.lumina.market.user.application.dto.CreateUserRequest;
import com.lumina.market.user.application.dto.UpdateUserRequest;
import com.lumina.market.user.application.port.in.UserInputPort;
import com.lumina.market.user.presentation.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserInputPort userInputPort;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserRequest request) {
        var userDTO = userInputPort.createUser(request);
        var response = UserResponse.fromDTO(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(response, "User created successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
        var userDTO = userInputPort.getUserById(id);
        var response = UserResponse.fromDTO(userDTO);
        return ResponseEntity.ok(ApiResponse.success(response, "User retrieved successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        var usersDTO = userInputPort.getAllUsers();
        var responses = usersDTO.stream()
                .map(UserResponse::fromDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses, "Users retrieved successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {
        var userDTO = userInputPort.updateUser(id, request);
        var response = UserResponse.fromDTO(userDTO);
        return ResponseEntity.ok(ApiResponse.success(response, "User updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userInputPort.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success(null, "User deleted successfully"));
    }
}