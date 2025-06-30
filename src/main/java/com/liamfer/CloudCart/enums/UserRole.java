package com.liamfer.CloudCart.enums;

public enum UserRole {
    STANDARD("role_standard"),
    ADMIN("role_admin");
    private String role;
    UserRole(String role){
        this.role = role;
    }
    public String getRole(){return this.role;}
}
