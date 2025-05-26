 package com.pregame.gametesting.model;


import java.util.Objects;

public class Certificate {
    private int certificateId;  // Corresponds to CertificatesID (auto-increment PK)
    private String name;
    private String code;
    private String field;
    private int testerId;       // Foreign Key to Tester table

    // Optional: Full Tester object, to be populated by a service layer
    private Tester tester;

    public Certificate() {
    }

    // Constructor for creating a new certificate (ID will be generated)
    public Certificate(String name, String code, String field, int testerId) {
        this.name = name;
        this.code = code;
        this.field = field;
        this.testerId = testerId;
    }

    // --- Getters and Setters for core fields ---
    public int getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(int certificateId) {
        this.certificateId = certificateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getTesterId() { // Direct getter for the ID
        return testerId;
    }

    public void setTesterId(int testerId) { // Direct setter for the ID
        this.testerId = testerId;
        // If the full Tester object exists and its ID doesn't match, nullify it
        if (this.tester != null && this.tester.getTesterId() != testerId) {
            this.tester = null;
        }
    }

    // --- Getter and Setter for the full Tester object (used by service layer) ---
    public Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
        if (this.tester != null) {
            this.testerId = this.tester.getTesterId(); // Keep ID in sync
        }

    }




}