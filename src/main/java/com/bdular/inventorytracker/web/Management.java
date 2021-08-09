package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/management")
public class Management {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping("/expenses")
    public ModelAndView getExpenses() {
        ModelAndView view = new ModelAndView("management/expenses");
        view.addObject("expenses_title", bundle.getString("LogIn"));
        return view;
    }

    @RequestMapping("/expense/{id}")
    public ModelAndView getExpense(@PathVariable String id) {
        ModelAndView view = new ModelAndView("management/expenses");
        return view;
    }

    @RequestMapping("/tasks")
    public ModelAndView getTasks() {
        ModelAndView view = new ModelAndView("management/tasks");
        view.addObject("tasks_title", bundle.getString("LogIn"));
        return view;
    }

    @RequestMapping("/task/{id}")
    public ModelAndView getTask(@PathVariable String id) {
        ModelAndView view = new ModelAndView("management/tasks");
        return view;
    }

    @RequestMapping(value = {"/work", "/"})
    public ModelAndView getManagement() {
        ModelAndView view = new ModelAndView("management/work-management");
        return view;
    }
}
