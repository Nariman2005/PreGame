
package com.pregame.gametesting.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Base class for all user types in the system.
 * Contains common properties and methods shared across Gamer, Developer, and Tester.
 */
public abstract class User implements Serializable {
    private int id;
    private String name;
    private String password;
    private int age;
    private String countryCode;
    private String telephone;
    private String email;



    // User type constants
    public static final String TYPE_GAMER = "GAMER";
    public static final String TYPE_DEVELOPER = "DEVELOPER";
    public static final String TYPE_TESTER = "TESTER";
    public static final String TYPE_ADMIN = "ADMIN";

    User() {
        // Default constructor
    }

    public User(String name, String password, String email) {

        this.name = name;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the type of user.
     * @return A string representing the user type
     */
    public abstract String getUserType();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}