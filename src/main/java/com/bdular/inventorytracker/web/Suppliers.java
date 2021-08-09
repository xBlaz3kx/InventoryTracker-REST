package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/suppliers")
public class Suppliers {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping("/")
    public ModelAndView getSuppliers() {
        ModelAndView view = new ModelAndView("main/supplier");
        view.addObject("suppliers_title", bundle.getString("LogIn"));
        view.addObject("suppliers_thead_supplier", bundle.getString("LogIn"));
        view.addObject("suppliers_thead_country", bundle.getString("LogIn"));
        view.addObject("suppliers_attribute", bundle.getString("LogIn"));
        view.addObject("suppliers_attribute_value", bundle.getString("LogIn"));
        view.addObject("update", bundle.getString("LogIn"));
        view.addObject("delete", bundle.getString("LogIn"));
        view.addObject("suppliers_add_supplier", bundle.getString("LogIn"));
        view.addObject("suppliers_name", bundle.getString("LogIn"));
        view.addObject("suppliers_company", bundle.getString("LogIn"));
        view.addObject("suppliers_address", bundle.getString("LogIn"));
        view.addObject("suppliers_post", bundle.getString("LogIn"));
        view.addObject("suppliers_country", bundle.getString("LogIn"));
        view.addObject("suppliers_VATID", bundle.getString("LogIn"));
        view.addObject("close", bundle.getString("LogIn"));
        view.addObject("clear", bundle.getString("LogIn"));
        view.addObject("add", bundle.getString("LogIn"));
        view.addObject("delete_supplier", bundle.getString("LogIn"));
        return view;
    }

    @RequestMapping("/supplier/{id}/create-order")
    public ModelAndView createOrder(@PathVariable String id) {
        ModelAndView view = new ModelAndView("main/supplier_orders");
        view.addObject("supplier_order_title", bundle.getString("LogIn"));
        view.addObject("supplier_details", bundle.getString("LogIn"));
        view.addObject("supplier_order_thead_product_name", bundle.getString("LogIn"));
        view.addObject("supplier_order_thead_product_barcode", bundle.getString("LogIn"));
        view.addObject("supplier_order_thead_product_SKU", bundle.getString("LogIn"));
        view.addObject("supplier_order_thead_product_purchase_price", bundle.getString("LogIn"));
        view.addObject("supplier_order_thead_current_stock", bundle.getString("LogIn"));
        view.addObject("supplier_order_thead_num_input", bundle.getString("LogIn"));
        view.addObject("supplier_order_make", bundle.getString("LogIn"));
        return view;
    }

    @RequestMapping("/supplier/{id}/delivery-reports")
    public ModelAndView deliveryReports(@PathVariable String id) {
        ModelAndView view = new ModelAndView("main/supplier");
        return view;
    }

    @RequestMapping("/supplier/{id}/delivery-reports/{delivery_id}")
    public ModelAndView getDeliveryReportPDF(@PathVariable String id, @PathVariable String delivery_id) {
        ModelAndView view = new ModelAndView("main/supplier");
        return view;
    }

}
