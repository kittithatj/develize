package com.pim.develize.service;

import com.pim.develize.entity.User;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.UserException;
import com.pim.develize.model.UserInfoModel;
import com.pim.develize.model.UserLoginModel;
import com.pim.develize.model.UserModel;
import com.pim.develize.repository.UserRepository;
import com.pim.develize.util.ObjectMapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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
            entity.setPassword(user.password);
            entity.setFirstName(user.firstName);
            entity.setLastName(user.lastName);
            entity.setRole(user.role);
            entity = userRepository.save(entity);
            return ObjectMapperUtils.map(entity, UserInfoModel.class);
        }
    }

    public UserInfoModel login(UserLoginModel userLoginModel) throws BaseException {
        Optional<User> user = userRepository.findByUsername(userLoginModel.username.toLowerCase());
        if(!user.isPresent()){
            throw UserException.loginFailed();
        }
        if(user.get().getPassword().equals(userLoginModel.getPassword())) {
            return ObjectMapperUtils.map(user.get(), UserInfoModel.class);
        }
        else{
            throw UserException.loginFailed();
        }
    }
}
