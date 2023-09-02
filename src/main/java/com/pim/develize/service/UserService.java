package com.pim.develize.service;

import com.pim.develize.entity.User;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.UserException;
import com.pim.develize.model.request.UserInfoModel;
import com.pim.develize.model.request.UserLoginModel;
import com.pim.develize.model.request.UserLoginResponseModel;
import com.pim.develize.model.request.UserModel;
import com.pim.develize.repository.UserRepository;
import com.pim.develize.util.ObjectMapperUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserInfoModel> getAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserInfoModel> usersInfo = ObjectMapperUtils.mapAll(users, UserInfoModel.class);
        return usersInfo;
    }

    public UserInfoModel createUser(UserModel user) throws BaseException{
        User entity = new User();
        if (userRepository.findByUsername(user.username).isPresent() || user.password.isBlank() || user.username.isBlank()){
            throw UserException.registerFailed();
        }else {
            entity.setUsername(user.username.toLowerCase());
            entity.setPassword(passwordEncoder.encode(user.password));
            entity.setFirstName(user.firstName);
            entity.setLastName(user.lastName);
            entity.setRole(user.role);
            entity = userRepository.save(entity);
            return ObjectMapperUtils.map(entity, UserInfoModel.class);
        }
    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public UserLoginResponseModel login(UserLoginModel userLoginModel) throws BaseException {
        Optional<User> user = userRepository.findByUsername(userLoginModel.username.toLowerCase());
        if(!user.isPresent()){
            throw UserException.loginFailed();
        }
//        if(user.get().getPassword().equals(userLoginModel.getPassword())){
//            return tokenService.tokenize(user.get());
//        }
        if(this.matchPassword(userLoginModel.getPassword(),user.get().getPassword())){
            UserLoginResponseModel userResponse = new UserLoginResponseModel();
            userResponse.firstName = user.get().getFirstName();
            userResponse.lastName = user.get().getLastName();
            userResponse.username = user.get().getUsername();
            userResponse.role = user.get().getRole();
            userResponse.token = tokenService.tokenize(user.get());
            return userResponse;
        }
        else{
            throw UserException.loginFailed();
        }
    }

    public String refreshToken() throws BaseException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long userId = (Long) authentication.getPrincipal();

        Optional<User> opt = userRepository.findById(userId);
        if(opt.isEmpty()){
            throw UserException.authorizeFailed();
        }
        User user = opt.get();
        return tokenService.tokenize(user);
    }
}
