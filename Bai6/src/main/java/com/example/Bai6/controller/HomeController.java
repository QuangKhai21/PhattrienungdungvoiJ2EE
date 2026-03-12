package com.example.bai5_tongvutanphat_2280602321.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Principal principal, org.springframework.ui.Model model) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
            return "home";
        }
        return "redirect:/login";
    }
}
