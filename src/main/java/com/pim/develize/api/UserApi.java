package com.pim.develize.api;

import com.pim.develize.exception.BaseException;
import com.pim.develize.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/user/create")
    public void sayHello() throws BaseException {
        userService.createUser("test01", "password", "admin");
    }
}
