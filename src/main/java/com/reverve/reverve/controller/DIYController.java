package com.reverve.reverve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DIYController {
    @GetMapping("/diy")
    public String diy(Model model) {
        return "diy";
    }
}