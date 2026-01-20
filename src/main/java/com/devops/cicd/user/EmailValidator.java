package com.devops.cicd.user;

public final class EmailValidator {

    private EmailValidator() {}

    public static boolean isValid(String email) {
        return email != null && email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$");
    }
}
