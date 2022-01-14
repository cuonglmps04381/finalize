package com.example.webdemo.controller;

import com.example.webdemo.model.dto.TestDTO;
import com.example.webdemo.model.dto.UserDTO;
import com.example.webdemo.model.Role;
import com.example.webdemo.model.User;
import com.example.webdemo.repository.RoleRepository;
import com.example.webdemo.repository.UserRepository;
import com.example.webdemo.service.UserService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @RequestMapping(value={"/admin", "/admin/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("layout-admin/singin");
            return modelAndView;
        } catch (Exception e) {
            throw e;
        }
    }
    @RequestMapping(value={"/admin/test"}, method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity loginT(@RequestBody TestDTO testDTO){
        try {
            UserDTO userDTO = new UserDTO();
            userDTO.setToken("ADADHUAHJBFI12312312");
            System.out.println("ffff");
            return ResponseEntity.ok().body(userDTO);
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value={"/admin/signup"}, method = RequestMethod.GET)
    public ModelAndView register(){
        try {
            ModelAndView modelAndView = new ModelAndView();
            UserDTO userDTO = new UserDTO();
            modelAndView.addObject("userDTO", userDTO);
            modelAndView.setViewName("layout-admin/signup");
            return modelAndView;
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserDTO userDTO, BindingResult bindingResult) {
        try {
            ModelAndView modelAndView = new ModelAndView();
            boolean valid = checkValidate(userDTO,bindingResult);
            if (valid == true) {
                    User userCreate = new User();
                    userCreate.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                    userCreate.setRemovalFlag(true);
                    Role userRole = roleRepository.findByRoleName("ADMIN");
                    //userCreate.setRoles(null);
                    userCreate.setCreatedDate(new Date());
                    userCreate.setLastName(userDTO.getLastName());
                    userCreate.setEmail(userDTO.getEmail());
                    userCreate.setFirstName(userDTO.getFirstName());
                    userCreate.setCreatedBy("admin");
                    userCreate.setRemovalFlag(true);
                    userCreate.setModifiedBy("admin");
                    userCreate.setModifiedDate(new Date());
                    userCreate.setIsActive(true);
                    userRepository.save(userCreate);
                    modelAndView.addObject("successMessage", "User has been registered successfully");
                    modelAndView.addObject("userDTO", new UserDTO());
                    modelAndView.setViewName("layout-admin/signup");
            } else {
                if (bindingResult.hasErrors()) {
                    modelAndView.setViewName("layout-admin/signup");
                }
            }
            return modelAndView;
        } catch (Exception e) {
            throw  e;
        }
    }

    public boolean checkValidate(UserDTO userDTO, BindingResult bindingResult) {
        UserDTO userExists = userService.findUserByEmail(userDTO.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.userDTO",
                            "There is already a user registered with the email provided");
            return false;
        }
        if (!userDTO.getRePassword().equals(userDTO.getPassword())) {
            bindingResult
                    .rejectValue("rePassword", "error.userDTO",
                            "password not match pls change");
            return false;
        }
        return true;
    }
    @RequestMapping(value={"/admin/home"}, method = RequestMethod.GET)
    public ModelAndView home(){
        try {
            ModelAndView modelAndView = new ModelAndView();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth instanceof AnonymousAuthenticationToken) {
                modelAndView.setViewName("layout-admin/singin");
                return modelAndView;
            }
            modelAndView.setViewName("layout-admin/content");
            return modelAndView;
        } catch (Exception e) {
            throw e;
        }
    }
    @RequestMapping(value={"/admin/account-list"}, method = RequestMethod.GET)
    public ModelAndView accountList(){
        try {
            ModelAndView modelAndView = new ModelAndView();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || auth instanceof AnonymousAuthenticationToken) {
                modelAndView.setViewName("layout-admin/singin");
                return modelAndView;
            }
            List<UserDTO> lstUser = userService.getAll();
            if (lstUser.size() > 0 && Objects.nonNull(lstUser)) {
                modelAndView.addObject("lstUser", lstUser);
            }
            modelAndView.setViewName("layout-admin/account-admin/account-list");
            return modelAndView;
        } catch (Exception e) {
            throw e;
        }
    }
    @RequestMapping(value={"/error"}, method = RequestMethod.GET)
    public ModelAndView error(){
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("404");
            return modelAndView;
        } catch (Exception e) {
            throw e;
        }
    }
}
