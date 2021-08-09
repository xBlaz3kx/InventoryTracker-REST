package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/")
public class Login {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping(value = {"/index", "/"})
    public ModelAndView getIndex() {
        ModelAndView indexPage = new ModelAndView("index");
        indexPage.addObject("index_title", bundle.getString("LoginTitle"));
        return indexPage;
    }

    @RequestMapping("/login")
    public ModelAndView login() {
        ModelAndView loginPage = new ModelAndView("login");
        loginPage.addObject("login_title", bundle.getString("LoginTitle"));
        loginPage.addObject("login_password", bundle.getString("Password"));
        loginPage.addObject("log_in", bundle.getString("LogIn"));
        loginPage.addObject("login_forgot_password", bundle.getString("ForgotPassword"));
        return loginPage;
    }

}
