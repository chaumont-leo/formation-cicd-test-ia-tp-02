package com.devops.cicd.user;

import com.devops.cicd.PasswordPolicy;

public class UserService {

    /**
     * Enregistre un utilisateur à partir des paramètres.
     *
     * Règles (voir spec) :
     * - crée un User
     * - renvoie l'utilisateur créé
     * - propage les erreurs si les données sont invalides
     */
    public User register(String email, String password, Role role) {
        try {
            return new User(email, password, role);
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
