package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/packages")
public class Packages {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping("/")
    public ModelAndView getPackages() {
        ModelAndView view = new ModelAndView("main/packages");
        view.addObject("packages_title", bundle.getString("LoginTitle"));
        return view;
    }

    @RequestMapping("/{id}")
    public ModelAndView getPackage(@PathVariable String id) {
        ModelAndView view = new ModelAndView("main/packages");
        return view;
    }
}
