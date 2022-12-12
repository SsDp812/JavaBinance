package com.sguProject.backendExchange.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class SignInController {
    @GetMapping("/login")
    public String signIn(Model model){
        System.out.println("/login");
        return "login";
    }

}
