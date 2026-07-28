package com.lumina.market.user.application.service;

import com.lumina.market.shared.exception.BusinessException;
import com.lumina.market.shared.exception.ResourceNotFoundException;
import com.lumina.market.user.application.dto.CreateUserRequest;
import com.lumina.market.user.application.dto.UpdateUserRequest;
import com.lumina.market.user.application.dto.UserDTO;
import com.lumina.market.user.application.port.in.UserInputPort;
import com.lumina.market.user.application.port.out.UserOutputPort;
import com.lumina.market.user.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserApplicationService implements UserInputPort {

    private final UserOutputPort userOutputPort;

    @Override
    @Transactional
    public UserDTO createUser(CreateUserRequest request) {
        // 1. Regla de negocio: Validar que el email no exista
        if (userOutputPort.existsByEmail(request.getEmail())) {
            throw new BusinessException("The email is already registered: " + request.getEmail());
        }

        // 2. Mapear Request a Domain Model
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword()) // En el futuro aquí iría el encriptado (BCrypt)
                .role(User.Role.CUSTOMER)
                .build();

        // 3. Guardar y mapear a DTO
        User savedUser = userOutputPort.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userOutputPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return mapToDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userOutputPort.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        User user = userOutputPort.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Actualizar solo los campos que vienen en el request
        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getEmail() != null) user.setEmail(request.getEmail());

        User updatedUser = userOutputPort.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userOutputPort.findById(id).isPresent()) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userOutputPort.deleteById(id);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}