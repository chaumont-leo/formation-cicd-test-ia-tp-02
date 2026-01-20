package com.devops.cicd.user;

import com.devops.cicd.PasswordPolicy;

public class User {

    private final String email;
    private final String password;
    private final Role role;

    private User(String email, String password, Role role) {
        this.email = email.trim();
        this.password = password;
        this.role = role;
    }

    public static User of(String email, String password, Role role) {
        if(!EmailValidator.isValid(email.trim())) {
            throw new IllegalArgumentException("email must be valid");
        }
        if(!PasswordPolicy.isStrong(password)) {
            throw new IllegalArgumentException("password must be strong");
        }
        if(role == null) {
            throw new IllegalArgumentException("role must not be null");
        }
        return new User(email, password, role);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean canAccessAdminArea() {
        return role.equals(Role.ADMIN);
    }

    // BONUS: vous pouvez ajouter equals/hashCode/toString si utile (non obligatoire)
}
