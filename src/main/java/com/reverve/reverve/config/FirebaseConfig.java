package com.reverve.reverve.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.context.annotation.Configuration;
import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FirebaseConfig {
    
    @PostConstruct
    public void initialize() throws IOException, FirebaseAuthException {
        // 1. Initialize Firebase
        FileInputStream serviceAccount = 
            new FileInputStream("src/main/resources/firebase-adminsdk.json");
        
        FirebaseOptions options = new FirebaseOptions.Builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("http://localhost:8081") // Add your URL
            .build();
        
        FirebaseApp.initializeApp(options);
        
        // 2. Initialize test data (only in development)
        initTestData();
    }

    private void initTestData() throws FirebaseAuthException {
        try {
            // Check if test user already exists
            FirebaseAuth.getInstance().getUserByEmail("test@reverve.com");
            System.out.println("Test user already exists, skipping creation");
        } catch (FirebaseAuthException e) {
            // Only create if user doesn't exist
            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("test@reverve.com")
                .setPassword("test123")
                .setDisplayName("Test User");
            
            UserRecord user = FirebaseAuth.getInstance().createUser(request);
            
            DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("users/" + user.getUid() + "/carbonFootprint");
            
            Map<String, Object> data = new HashMap<>();
            data.put("transportation", 45);
            data.put("energy", 30);
            data.put("food", 25);
            
            ref.setValueAsync(data);
            System.out.println("Created test user with UID: " + user.getUid());
        }
    }
}
