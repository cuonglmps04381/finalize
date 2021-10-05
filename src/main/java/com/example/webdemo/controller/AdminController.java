package com.example.webdemo.controller;

import com.example.webdemo.dto.UserDTO;
import com.example.webdemo.model.Role;
import com.example.webdemo.model.User;
import com.example.webdemo.repository.RoleRepository;
import com.example.webdemo.repository.UserRepository;
import com.example.webdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

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

    @RequestMapping(value={"/admin", "/admin/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("layout-admin/login");
            return modelAndView;
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value={"/admin/register"}, method = RequestMethod.GET)
    public ModelAndView register(){
        try {
            ModelAndView modelAndView = new ModelAndView();
            UserDTO userDTO = new UserDTO();
            modelAndView.addObject("userDTO", userDTO);
            modelAndView.setViewName("layout-admin/register");
            return modelAndView;
        } catch (Exception e) {
            throw e;
        }
    }

    @RequestMapping(value = "/admin/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid UserDTO userDTO, BindingResult bindingResult) throws IOException {
        try {
            ModelAndView modelAndView = new ModelAndView();
            boolean valid = checkValidate(userDTO,bindingResult);
            if (valid == true) {
                    User userCreate = new User();
                    userCreate.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
                    userCreate.setRemovalFlag(true);
                    Role userRole = roleRepository.findByRoleName("ADMIN");
                    userCreate.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
                    userCreate.setCreatedDate(new Date());
                    userCreate.setLastName(userDTO.getLastName());
                    userCreate.setEmail(userDTO.getEmail());
                    userCreate.setFirstName(userDTO.getFirstName());
                    userCreate.setCreatedBy("admin");
                    userCreate.setRemovalFlag(true);
                    userCreate.setModifiedBy("admin");
                    userCreate.setModifiedDate(new Date());
                    userRepository.save(userCreate);
                    modelAndView.addObject("successMessage", "User has been registered successfully");
                    modelAndView.addObject("userDTO", new UserDTO());
                    modelAndView.setViewName("layout-admin/register");
            } else {
                if (bindingResult.hasErrors()) {
                    modelAndView.setViewName("layout-admin/register");
                }
            }
            return modelAndView;
        } catch (Exception e) {
            throw  e;
        }
    }

    public boolean checkValidate(UserDTO userDTO, BindingResult bindingResult) {
        User userExists = userService.findUserByEmail(userDTO.getEmail());
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

        if (userDTO.isChecked() == false) {
            bindingResult
                    .rejectValue("checked", "error.userDTO",
                            "pls check");
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
                modelAndView.setViewName("layout-admin/login");
                return modelAndView;
            }
            modelAndView.setViewName("layout-admin/home");
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
