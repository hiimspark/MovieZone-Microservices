package org.example.gateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {
    @GetMapping("/")
    public String main(Model model) {return "home";}
    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }
}