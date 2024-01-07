package com.pim.develize.api;

import com.pim.develize.exception.BaseException;
import com.pim.develize.model.request.*;
import com.pim.develize.model.response.UserListResponse;
import com.pim.develize.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserApi {

    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserInfoModel register(@RequestBody UserModel user) throws BaseException {
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseModel> login(@RequestBody UserLoginModel user) throws BaseException {
        return ResponseEntity.ok(userService.login(user));
    }

    @GetMapping("/get-list")
    public UserListResponse getAllUser() throws BaseException {
        return userService.getAllUsers();
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<String> refreshToken() throws BaseException{
        String token = userService.refreshToken();
        return ResponseEntity.ok(token);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id)throws BaseException{
        userService.deleteUserById(id);
        return ResponseEntity.ok("Deleted User Successfully");
    }

    @PutMapping("/approve")
    public ResponseEntity<UserInfoModel> approveUser(@RequestBody ApproveUserModel user) throws BaseException {
        UserInfoModel res = userService.assignRole(user.getUserId(), user.getRole());
        return ResponseEntity.ok(res);
    }

    @PutMapping("/edit")
    public ResponseEntity<UserInfoModel> approveUser(@RequestBody UserInfoModel user) throws BaseException {
        UserInfoModel res = userService.editUser(user);
        return ResponseEntity.ok(res);
    }
}
