package de.cinetastisch.backend;

import org.springframework.core.env.Environment;

public class ProfileManager {

    private Environment environment;

    public void getActiveProfiles() {
        for(String profileName : environment.getActiveProfiles()){
            System.out.println("Active Profiles: " + profileName);
        }
    }
}
