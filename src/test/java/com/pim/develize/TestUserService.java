package com.pim.develize;

import com.pim.develize.exception.BaseException;
import com.pim.develize.repository.UserRepository;
import com.pim.develize.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUserService {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void testCreateUser() throws BaseException {
        userService.createUser("Test02","123456","member");
        Assertions.assertEquals(userRepository.findByUsername("Test02").get().getPassword(),"123456");
    }
}
