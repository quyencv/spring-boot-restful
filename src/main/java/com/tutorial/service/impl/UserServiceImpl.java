package com.tutorial.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tutorial.constant.Role;
import com.tutorial.dto.UserDTO;
import com.tutorial.exception.custom.BusinessException;
import com.tutorial.mapper.UserMapper;
import com.tutorial.model.User;
import com.tutorial.repository.UserRepository;
import com.tutorial.service.UserService;
import com.tutorial.service.utils.QueryService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl extends QueryService<User, UserDTO> implements UserService {

    private UserMapper userMapper;

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO save(UserDTO userDTO) throws Exception {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new BusinessException("Username is already taken");
        }

        // Create new user's account
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User user = userMapper.toEntity(userDTO);
        User resultSave = userRepository.save(user);
        UserDTO result = userMapper.toDTO(resultSave);
        return result;
    }

    @Override
    public UserDTO update(UserDTO userDTO) throws Exception {
        User user = userMapper.toEntity(userDTO);
        User resultUpdate = userRepository.save(user);
        UserDTO result = userMapper.toDTO(resultUpdate);
        return result;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            return Optional.empty();
        }
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            return Optional.ofNullable(userMapper.toDTO(userOpt.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> findAll(UserDTO condition) {
        Specification<User> spec = createSpec(condition);
        List<User> users = userRepository.findAll(spec);
        List<UserDTO> result = userMapper.toDTO(users);
        return result;
    }

    @Override
    public List<UserDTO> findAll(UserDTO condition, Sort sort) {
        Specification<User> spec = createSpec(condition);
        List<User> users = userRepository.findAll(spec, sort);
        List<UserDTO> result = userMapper.toDTO(users);
        return result;
    }

    @Override
    public Page<UserDTO> findAll(UserDTO condition, Pageable pageable) {
        Specification<User> spec = createSpec(condition);
        Page<User> users = userRepository.findAll(spec, pageable);
        Page<UserDTO> result = userMapper.toDTO(users);
        return result;
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        Optional<UserDTO> result = userMapper.toDTO(userOpt);
        return result;
    }

    @Override
    public Long deleteById(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public void updateUserRole(String username) {
        userRepository.updateUserRole(username, Role.ADMIN);
    }

}
