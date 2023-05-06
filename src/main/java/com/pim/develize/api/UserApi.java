package com.pim.develize.api;

import com.pim.develize.exception.BaseException;
import com.pim.develize.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/test-create")
    public void testCreateUser() throws BaseException {
        userService.createUser("test01", "password", "admin");
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Hello Moon!";
    }
}
