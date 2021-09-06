package com.example.webdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @RequestMapping(value={"/"}, method = RequestMethod.GET)
    public ModelAndView login(){
        try {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("layout-user/content");
            return modelAndView;
        } catch (Exception e) {
            throw e;
        }
    }

}
