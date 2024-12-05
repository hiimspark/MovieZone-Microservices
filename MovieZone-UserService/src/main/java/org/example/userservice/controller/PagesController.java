package org.example.userservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("/reg")
    public String reg(Model model) {
        return "reg";
    }
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

}