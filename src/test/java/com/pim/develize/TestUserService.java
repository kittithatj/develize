package com.pim.develize;

import com.pim.develize.exception.BaseException;
import com.pim.develize.model.UserModel;
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
        UserModel user = new UserModel();
        user.username = "Thanathorn31";
        user.firstName = "Thanathorn";
        user.lastName = "Juengrungruengkit";
        user.password = "31313131";
        user.role = "Nayok";
        userService.createUser(user);
        Assertions.assertEquals(userRepository.findByUsername("Thanathorn31").get().getPassword(),"31313131");
    }
}
