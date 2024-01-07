package com.pim.develize.service;

import com.pim.develize.entity.JobAssessment;
import com.pim.develize.entity.User;
import com.pim.develize.exception.BaseException;
import com.pim.develize.exception.UserException;
import com.pim.develize.model.MailModel;
import com.pim.develize.model.request.UserInfoModel;
import com.pim.develize.model.request.UserLoginModel;
import com.pim.develize.model.request.UserLoginResponseModel;
import com.pim.develize.model.request.UserModel;
import com.pim.develize.model.response.UserListResponse;
import com.pim.develize.repository.JobAssessmentRepository;
import com.pim.develize.repository.UserRepository;
import com.pim.develize.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Value("${config.fe.local.url}")
    private String url;
    @Autowired
    private MailService mailService;
    @Autowired
    private JobAssessmentRepository jobAssessmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserListResponse getAllUsers() throws UserException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long userIdA = (Long) authentication.getPrincipal();
        User userA = userRepository.findById(userIdA).get();
        if(!userA.getRole().equals("Administrator")){
            throw UserException.noPermission();
        }

        List<User> usersA = userRepository.findAllByIsApproved(true);
        List<UserInfoModel> usersAres = ObjectMapperUtils.mapAll(usersA, UserInfoModel.class);

        List<User> usersB = userRepository.findAllByIsApproved(false);
        List<UserInfoModel> usersBres = ObjectMapperUtils.mapAll(usersB, UserInfoModel.class);

        UserListResponse userList = new UserListResponse();
        userList.setApproved(usersAres);
        userList.setNotApproved(usersBres);

        return userList;
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
            entity.setEmail(user.email);
            entity.setIsApproved(false);
            entity.setRole("Not-Assigned");
            entity = userRepository.save(entity);
            UserInfoModel res = ObjectMapperUtils.map(entity, UserInfoModel.class);
            res.setIsApproved(false);
            return res;
        }
    }

    public UserInfoModel editUser(UserInfoModel input) throws UserException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long userIdA = (Long) authentication.getPrincipal();
        User userA = userRepository.findById(userIdA).get();
        if(!userA.getRole().equals("Administrator")){
            throw UserException.noPermission();
        }

        User user = userRepository.findById(input.user_id).get();
        user.setFirstName(input.firstName);
        user.setLastName(input.lastName);
        user.setUsername(input.username);
        user.setEmail(input.email);
        user.setRole(input.role);
        user.setIsApproved(input.isApproved);
        user = userRepository.save(user);
        return ObjectMapperUtils.map(user, UserInfoModel.class);
    }

    public UserInfoModel assignRole(Long userId, String role) throws UserException {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long userIdA = (Long) authentication.getPrincipal();
        User userA = userRepository.findById(userIdA).get();
        if(!userA.getRole().equals("Administrator")){
            throw UserException.noPermission();
        }
       User user = userRepository.findById(userId).get();
       user.setRole(role);
       user.setIsApproved(true);
       user = userRepository.save(user);

       if(user.getEmail() != null){
           MailModel mail = new MailModel();
           mail.setSubject("Your User Account Has Been Approved!");
           mail.setMessage("<html><head>" +
                   "<style>" +
                   "td {\n" +
                   "    border-radius: 10px;\n" +
                   "}\n" +
                   "\n" +
                   "td a {\n" +
                   "    padding: 8px 12px;\n" +
                   "    border: 1px solid #1F83FF;\n" +
                   "    border-radius: 10px;\n" +
                   "    font-family: Arial, Helvetica, sans-serif;\n" +
                   "    font-size: 14px;\n" +
                   "    color: #ffffff; \n" +
                   "    text-decoration: none;\n" +
                   "    font-weight: bold;\n" +
                   "    display: inline-block;  \n" +
                   "}"+
                   "</style>" +
                   "</head>" +
                   "<h3>Hello "+user.getFirstName()+"! Your registered account has been verified!</h3>" +
                   "<p>You have been assign to be <b>"+user.getRole()+"</b>!</p>" +
                   "<table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\">" +
                   "  <tr>" +
                   "      <td>" +
                   "          <table cellspacing=\"0\" cellpadding=\"0\">" +
                   "              <tr>" +
                   "                  <td class=”button” bgcolor=\"#1F83FF\">" +
                   "                      <a style=\"background-color: #1F83FF; color: white;\"  class=”link” href=\"http://"+url+"/login\" target=\"_blank\">" +
                   "                          Click Here To Login!            " +
                   "                      </a>" +
                   "                  </td>" +
                   "              </tr>" +
                   "          </table>" +
                   "      </td>" +
                   "  </tr>" +
                   "</table></html>");
           mailService.sendEmail(user.getEmail(),mail);
       }

       return ObjectMapperUtils.map(user, UserInfoModel.class);
    }

    public void deleteUserById(Long id) throws BaseException {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Long userId = (Long) authentication.getPrincipal();
        User user = userRepository.findById(userId).get();
        if(!user.getRole().equals("Administrator")){
            throw UserException.noPermission();
        }
        Optional<User> opt = userRepository.findById(id);
        if(opt.isPresent()) {

            List<JobAssessment> jList = jobAssessmentRepository.findByAssessBy(opt.get());
            for(JobAssessment j : jList){
                jobAssessmentRepository.delete(j);
            }

            userRepository.delete(opt.get());

        }else{
            throw UserException.notFound();
        }

    }

    public boolean matchPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public UserLoginResponseModel login(UserLoginModel userLoginModel) throws BaseException {
        Optional<User> user = userRepository.findByUsername(userLoginModel.username.toLowerCase());
        if(user.isEmpty()){
            throw UserException.loginFailed();
        }
        if(!user.get().getIsApproved()){
            throw UserException.loginFailed();
        }

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
