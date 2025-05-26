package com.pregame.gametesting.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tester user implementation for the game testing system.
 */
public class Tester extends User  {

    private int testerId;
    private String rank;
    private List<Certificate> certificates;
    private List<Review> reviews;

    public Tester() {
        super();
        this.certificates = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public Tester(String name, String password, String email) {
        super(name, password, email);
        this.certificates = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    @Override
    public String getUserType() {
        return User.TYPE_TESTER;
    }


    public int getTesterId() {
        return testerId;
    }

    public void setTesterId(int testerId) {
        this.testerId = testerId;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public List<Certificate> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<Certificate> certificates) {
        this.certificates = certificates;
    }

    public void addCertificate(Certificate certificate) {
        this.certificates.add(certificate);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

}