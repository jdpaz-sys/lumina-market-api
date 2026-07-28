package com.lumina.market.user.application.port.in;

import com.lumina.market.user.application.dto.CreateUserRequest;
import com.lumina.market.user.application.dto.UpdateUserRequest;
import com.lumina.market.user.application.dto.UserDTO;

import java.util.List;

public interface UserInputPort {

    UserDTO createUser(CreateUserRequest request);

    UserDTO getUserById(Long id);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(Long id, UpdateUserRequest request);

    void deleteUser(Long id);
}