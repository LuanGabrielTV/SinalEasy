package com.sinalez.sinaleasy_back.services.logic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.dtos.UserRecordDTO;
import com.sinalez.sinaleasy_back.entities.User;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.UserNotFoundException;
import com.sinalez.sinaleasy_back.repositories.UserRepository;

@Service
public class UserService {
    public final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserRecordDTO userRecordDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRecordDTO, user);
        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }


}

