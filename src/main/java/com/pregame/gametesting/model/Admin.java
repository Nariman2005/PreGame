package com.pregame.gametesting.model;

import java.time.LocalDateTime;

public class Admin extends User {
    
    private int adminId;
    private String role;
    private String accessLevel;
    private LocalDateTime lastActionDate;
    

    public Admin() {
        super();
    }
    

    public Admin(String email, String name, String password,
                 String role, String accessLevel) {
        super(name, password,email);
        this.role = role;
        this.accessLevel = accessLevel;
    }
    
    @Override
    public String getUserType() {

        return User.TYPE_ADMIN;
    }
    
    // Getters and Setters
    
    public int getAdminId() {
        return adminId;
    }
    
    public void setAdminId(int adminId) {
        this.adminId = adminId;
        setId(adminId); // Also set in parent class
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getAccessLevel() {
        return accessLevel;
    }
    
    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
    
    public LocalDateTime getLastActionDate() {
        return lastActionDate;
    }
    
    public void setLastActionDate(LocalDateTime lastActionDate) {
        this.lastActionDate = lastActionDate;
    }
    

}
