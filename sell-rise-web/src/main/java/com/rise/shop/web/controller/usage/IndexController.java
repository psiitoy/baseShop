package com.rise.shop.web.controller.usage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
public class IndexController {

    @RequestMapping(value = {"/index", "/"}, method = RequestMethod.GET)
    public String index(
            @RequestParam(value = "locale", required = false) Locale locale,
            HttpServletRequest request,
            HttpServletResponse response) {
        return "redirect:/artist/index";
    }

    @RequestMapping(value = {"/nologin"}, method = RequestMethod.GET)
    public String nologin() {
        return "desc";
    }
}
