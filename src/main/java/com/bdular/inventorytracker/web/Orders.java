package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/orders")
public class Orders {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping("/")
    public ModelAndView getOrders() {
        ModelAndView view = new ModelAndView("main/orders");
        view.addObject("order_title", bundle.getString("LoginTitle"));
        view.addObject("order_customer", bundle.getString("LoginTitle"));
        view.addObject("orders_thead_customer", bundle.getString("LoginTitle"));
        view.addObject("orders_thead_orderdate", bundle.getString("LoginTitle"));
        view.addObject("orders_thead_attribute", bundle.getString("LoginTitle"));
        view.addObject("orders_thead_attribute_value", bundle.getString("LoginTitle"));
        view.addObject("orders_thead_attribute_value", bundle.getString("LoginTitle"));
        view.addObject("export_order_excel", bundle.getString("LoginTitle"));
        view.addObject("export_order_pdf", bundle.getString("LoginTitle"));
        view.addObject("order_start_date", bundle.getString("LoginTitle"));
        view.addObject("order_end_date", bundle.getString("LoginTitle"));
        view.addObject("export_orders", bundle.getString("LoginTitle"));
        view.addObject("update", bundle.getString("LoginTitle"));
        return view;
    }

    @RequestMapping("/{id}")
    public ModelAndView getOrder(@PathVariable String id) {
        ModelAndView view = new ModelAndView("main/orders");
        return view;
    }

    @RequestMapping("/new-order")
    public ModelAndView createNewOrder() {
        ModelAndView view = new ModelAndView("main/orders");
        return view;
    }
}
