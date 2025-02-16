package com.sinalez.sinaleasy_back.services.logic;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sinalez.sinaleasy_back.domains.User;
import com.sinalez.sinaleasy_back.dtos.RegisterDTO;
import com.sinalez.sinaleasy_back.dtos.UserDTO;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.userExceptions.UserAlreadyExists;
import com.sinalez.sinaleasy_back.exceptions.customExceptions.userExceptions.UserNotFoundException;
import com.sinalez.sinaleasy_back.repositories.UserRepository;

@Service
public class UserService {
    public final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserDTO userRequestDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRequestDTO, user);
        return userRepository.save(user);
    }

    public User registerUser(RegisterDTO registerDTO) {
        if (
            userRepository.findByUserLogin(registerDTO.login()) != null ||
            userRepository.existsByUserEmail(registerDTO.email())
        ) {
            throw new UserAlreadyExists();
        }
        String encryptedPassword = new BCryptPasswordEncoder()
                .encode(registerDTO.password());
        User newUser = new User(
                registerDTO.login(), 
                encryptedPassword, 
                registerDTO.role());
        return userRepository.save(newUser);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }

    public UUID getUserIdByUserLogin(String userLogin) {
        return userRepository.findUserIdByUserLogin(userLogin)
                .orElseThrow(UserNotFoundException::new);
    }

    public boolean userEmailExists(String email) {
        return userRepository.existsByUserEmail(email);
    }

}

