package com.lumina.market.user.application.port.out;

import com.lumina.market.user.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserOutputPort {

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findAll();

    void deleteById(Long id);

    boolean existsByEmail(String email);
}