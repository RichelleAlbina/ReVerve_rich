// src/main/java/com/reverve/reverve/controller/AuthController.java
package com.reverve.reverve.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    
    @GetMapping("/")
    public String homeRedirect() {
        return "redirect:/home";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          Model model) {
        try {
            // 1. Create Firebase auth user
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(email)
                .setPassword(password)
                .setDisplayName(username);
            
            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

            // 2. Store additional user data
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
            Map<String, Object> userData = new HashMap<>();
            userData.put("username", username);
            userData.put("email", email);
            userData.put("createdAt", ServerValue.TIMESTAMP);
            
            ref.child(userRecord.getUid()).setValueAsync(userData);

            // 3. Redirect to login with success message
            model.addAttribute("success", "Registration successful! Please login");
            return "login";

        } catch (FirebaseAuthException e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "register";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {
        try {
            // 1. Verify credentials by trying to get user
            UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmail(email);
            
            // 2. Store user in session
            session.setAttribute("user", userRecord);
            
            // 3. Redirect to home
            return "redirect:/home";
            
        } catch (FirebaseAuthException e) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}