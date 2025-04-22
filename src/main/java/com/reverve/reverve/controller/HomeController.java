package com.reverve.reverve.controller;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.reverve.reverve.service.SustainabilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private SustainabilityService sustainabilityService;
    
    @GetMapping("/home")
    public String home(Model model, HttpSession session) throws FirebaseAuthException {
        UserRecord user = (UserRecord) session.getAttribute("user");
        if (user == null) return "redirect:/login";
        
        model.addAttribute("carbonPercentage", 
            sustainabilityService.calculateCarbonPercentage(user.getUid()));
        
        model.addAttribute("sustainabilityScore", 
            sustainabilityService.calculateSustainabilityScore(user.getUid()));
        
        return "home";
    }
}