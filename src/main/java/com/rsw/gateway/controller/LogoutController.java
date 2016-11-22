package com.rsw.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by DAlms on 10/23/16.
 * Logout functionality isn't right yet... work in progress
 */
@Controller
public class LogoutController {

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        return "/";
    }
}
