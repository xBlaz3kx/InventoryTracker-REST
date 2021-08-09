package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/statistics")
public class Statistics {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping("/")
    public ModelAndView getStatisticsIndex() {
        ModelAndView view = new ModelAndView("main/statistics");
        view.addObject("stats_title", bundle.getString("LogIn"));
        view.addObject("stats_orders", bundle.getString("LogIn"));
        view.addObject("stats_suppliers", bundle.getString("LogIn"));
        view.addObject("stats_products", bundle.getString("LogIn"));
        view.addObject("stats_customers", bundle.getString("LogIn"));
        view.addObject("stats_dates", bundle.getString("LogIn"));
        view.addObject("stats_today", bundle.getString("LogIn"));
        view.addObject("stats_current_week", bundle.getString("LogIn"));
        view.addObject("stats_current_month", bundle.getString("LogIn"));
        view.addObject("stats_quarter", bundle.getString("LogIn"));
        view.addObject("stats_export", bundle.getString("LogIn"));
        view.addObject("stats_calendar", bundle.getString("LogIn"));
        return view;
    }

}
