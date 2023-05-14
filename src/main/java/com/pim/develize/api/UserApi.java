package com.pim.develize.api;

import com.pim.develize.exception.BaseException;
import com.pim.develize.model.UserInfoModel;
import com.pim.develize.model.UserLoginModel;
import com.pim.develize.model.UserModel;
import com.pim.develize.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserInfoModel register(@RequestBody UserModel user) throws BaseException {
        System.out.println(user.firstName+user.lastName+user.username+user.password+user.role);
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public UserInfoModel login(@RequestBody UserLoginModel user) throws BaseException {
        return userService.login(user);
    }

    @GetMapping("/get")
    public List<UserInfoModel> getAllUser() {
        return userService.getAllUsers();
    }
}
