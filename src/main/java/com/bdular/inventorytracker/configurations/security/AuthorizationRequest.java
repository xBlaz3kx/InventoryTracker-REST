package com.bdular.inventorytracker.configurations.security;

public class AuthorizationRequest {

    String username;
    String password;

    public AuthorizationRequest() {
    }

    public AuthorizationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
