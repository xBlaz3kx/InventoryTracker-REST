package com.bdular.inventorytracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ResourceBundle;

@Controller
@RequestMapping("/support")
public class Support {

    static ResourceBundle bundle = ResourceBundle.getBundle("web-translations");

    @RequestMapping("/contact")
    public ModelAndView support() {
        ModelAndView supportPage = new ModelAndView("support/contact");
        supportPage.addObject("support_contact_title", bundle.getString("LoginTitle"));
        supportPage.addObject("support_contact_name", bundle.getString("LoginTitle"));
        supportPage.addObject("support_contact_surname", bundle.getString("LoginTitle"));
        supportPage.addObject("support_contact_email", bundle.getString("LoginTitle"));
        supportPage.addObject("support_contact_subject", bundle.getString("LoginTitle"));
        supportPage.addObject("support_contact_description", bundle.getString("LoginTitle"));
        supportPage.addObject("support_send_inquiry", bundle.getString("LoginTitle"));
        return supportPage;
    }

    @RequestMapping("/info")
    public ModelAndView info() {
        ModelAndView infoPage = new ModelAndView("support/info");
        infoPage.addObject("info_title", bundle.getString("LoginTitle"));
        infoPage.addObject("info_theader_title", bundle.getString("LoginTitle"));
        infoPage.addObject("info_theader_link", bundle.getString("LoginTitle"));
        infoPage.addObject("info_theader_othr", bundle.getString("LoginTitle"));
        infoPage.addObject("info_functional_docs", bundle.getString("LoginTitle"));
        infoPage.addObject("info_technical_docs", bundle.getString("LoginTitle"));
        infoPage.addObject("info_error_reporting_guide", bundle.getString("LoginTitle"));
        return infoPage;
    }
}
