package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/customers")
public class Customers {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping("/")
    public ModelAndView getCustomers() {
        ModelAndView view = new ModelAndView("main/customer");
        view.addObject("customers_title", bundle.getString("LoginTitle"));
        view.addObject("customer_add_customer", bundle.getString("LoginTitle"));
        view.addObject("customer_company", bundle.getString("LoginTitle"));
        view.addObject("customers_title", bundle.getString("LoginTitle"));
        view.addObject("customer_name", bundle.getString("LoginTitle"));
        view.addObject("customer_surname", bundle.getString("LoginTitle"));
        view.addObject("customers_title", bundle.getString("LoginTitle"));
        view.addObject("customer_address", bundle.getString("LoginTitle"));
        view.addObject("customer_post", bundle.getString("LoginTitle"));
        view.addObject("customer_VATID", bundle.getString("LoginTitle"));
        view.addObject("close", bundle.getString("LoginTitle"));
        view.addObject("clear", bundle.getString("LoginTitle"));
        view.addObject("add", bundle.getString("LoginTitle"));
        view.addObject("customer_attribute", bundle.getString("LoginTitle"));
        view.addObject("customer_attribute_value", bundle.getString("LoginTitle"));
        view.addObject("customer_update_value", bundle.getString("LoginTitle"));
        view.addObject("delete", bundle.getString("LoginTitle"));
        view.addObject("customer_delete", bundle.getString("LoginTitle"));
        return view;
    }

    @RequestMapping("/{id}")
    public ModelAndView getCustomer(@PathVariable String id) {
        ModelAndView view = new ModelAndView("main/customer");
        view.addObject("index_title", bundle.getString("LoginTitle"));
        return view;
    }

}
