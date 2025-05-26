package com.pregame.gametesting.beans;

import java.io.Serializable;

/**
 * JavaBean to handle registration form data
 */
public class RegistrationBean implements Serializable {


    private String name;
    private String email;
    private String password;
    private String confirmPassword;
    private String countryCode;
    private String telephone;
    private int age;
    private String userType;

    // Additional fields based on user type
    private int level; // for gamers
    private String companyName; // for developers
    private String rank; // for testers

    // Default constructor
    public RegistrationBean() {
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * Validates the registration data
     * @return error message if validation fails, null if validation passes
     */
    public String validate() {
        if (name == null || name.trim().isEmpty()) {
            return "Name is required";
        }
        if (email == null || email.trim().isEmpty()) {
            return "Email is required";
        }
        if (!email.contains("@")) {
            return "Invalid email format";
        }
        if (password == null || password.trim().isEmpty()) {
            return "Password is required";
        }
        if (password.length() < 6) {
            return "Password must be at least 6 characters";
        }
        if (confirmPassword == null || !confirmPassword.equals(password)) {
            return "Passwords do not match";
        }
        if (age <= 0) {
            return "Age must be a positive number";
        }

        // Validate user type specific fields
        if ("gamer".equals(userType) && level <= 0) {
            return "Level must be a positive number";
        }
        if ("developer".equals(userType) && (companyName == null || companyName.trim().isEmpty())) {
            return "Company name is required for developers";
        }
        if ("tester".equals(userType) && (rank == null || rank.trim().isEmpty())) {
            return "Rank is required for testers";
        }

        return null; // validation passed
    }
}
