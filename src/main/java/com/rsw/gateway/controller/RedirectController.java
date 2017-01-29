package com.rsw.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by DAlms on 12/16/16.
 */
@Controller
public class RedirectController {

    @RequestMapping("/")
    public String rootToSwagger() {
        return "redirect:/swagger-ui.html";
    }
}
