// src/main/java/com/reverve/reverve/controller/MapsController.java
package com.reverve.reverve.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MapsController {
    @GetMapping("/maps")
    public String maps(@RequestParam(required = false) String store, Model model) {
        model.addAttribute("store", store);
        return "maps";
    }
}