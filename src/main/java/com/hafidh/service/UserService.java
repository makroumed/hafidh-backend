package com.hafidh.service;

import com.hafidh.dto.UserDTO;
import com.hafidh.dto.UserProfileDto;
import com.hafidh.entity.User;
import com.hafidh.exception.UserNotFoundException;
import com.hafidh.mapper.UserMapper;
import com.hafidh.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private UserMapper userMapper;

    @Cacheable(value = "user", key = "#userId")
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDTO(user);
    }
    @Cacheable(value = "user", key = "#userEmail")
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDTO(user);
    }

    @CacheEvict(value = "user", key = "#userId")
    public void updateUserProfile(Long userId, UserProfileDto dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        userRepository.save(user);
    }
}