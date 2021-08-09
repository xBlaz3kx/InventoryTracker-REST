package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/products")
public class Products {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping("/")
    public ModelAndView getProducts() {
        ModelAndView view = new ModelAndView("main/product");
        view.addObject("products_title", bundle.getString("LoginTitle"));
        view.addObject("products_thead_product_name", bundle.getString("LoginTitle"));
        view.addObject("products_thead_product_reference", bundle.getString("LoginTitle"));
        view.addObject("products_thead_product_barcode", bundle.getString("LoginTitle"));
        view.addObject("products_attribute", bundle.getString("LoginTitle"));
        view.addObject("products_attribute_value", bundle.getString("LoginTitle"));
        view.addObject("delete", bundle.getString("LoginTitle"));
        view.addObject("update", bundle.getString("LoginTitle"));
        view.addObject("products_add_product", bundle.getString("LoginTitle"));
        view.addObject("product_name", bundle.getString("LoginTitle"));
        view.addObject("product_barcode", bundle.getString("LoginTitle"));
        view.addObject("product_price", bundle.getString("LoginTitle"));
        view.addObject("product_purchase_price", bundle.getString("LoginTitle"));
        view.addObject("product_SKU", bundle.getString("LoginTitle"));
        view.addObject("product_stock", bundle.getString("LoginTitle"));
        view.addObject("product_supplier", bundle.getString("LoginTitle"));
        view.addObject("product_VAT", bundle.getString("LoginTitle"));
        view.addObject("close", bundle.getString("LoginTitle"));
        view.addObject("clear", bundle.getString("LoginTitle"));
        view.addObject("add", bundle.getString("LoginTitle"));
        view.addObject("delete_product", bundle.getString("LoginTitle"));
        return view;
    }

    @RequestMapping("/{id}")
    public ModelAndView getProduct(@PathVariable String id) {
        ModelAndView view = new ModelAndView("main/product");
        return view;
    }

}
