package com.example.api.config;

public class TestData {
    public static final String ADMIN_USERNAME = "admin";
    public static final String ADMIN_PASSWORD = "password123";
    // You could store a generated token here if it's meant to be shared across tests
    public static String AUTH_TOKEN; // Can be set programmatically after authentication
    public static String BASE_URL = "https://restful-booker.herokuapp.com";
}