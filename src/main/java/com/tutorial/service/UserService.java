package com.tutorial.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.tutorial.dto.UserDTO;
import com.tutorial.model.User;
import com.tutorial.service.utils.BaseService;

public interface UserService extends BaseService<User, UserDTO> {

    boolean existsByUsername(String username);

    Optional<UserDTO> findByUsername(String username);

    @Transactional
    void updateUserRole(String username);
}
