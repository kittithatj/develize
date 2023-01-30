package com.pim.develize.service;

import com.pim.develize.entity.User;
import com.pim.develize.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password, String role) throws Exception{
        User entity = new User();
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception();
        }else {
            entity.setUsername(username);
            entity.setPassword(password);
            entity.setRole(role);
            return userRepository.save(entity);
        }
    }
}
