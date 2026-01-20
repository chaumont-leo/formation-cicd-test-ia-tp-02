## Partie 1

### Règles métiers 

- Un User est composé d'un email, d'un mot de passe et d'un role (USER ou ADMIN)
- Au moment de l'inscription si les valuers fournies ne respectent pas les critères de validations cela doit lever une exception avec un message d'erreur


### Cas Nominaux, Limites et d'Erreurs

Voici une liste de cas avec le 3-tuple de valeur nécessaire pour créer un utilisateur et le retour attendu.
La dernière colonne correspond au résultat que l'on peut attendre de méthode canAccessAdminArea()

#### Ecrit par moi

##### isValid (Email)

| Entrée                 | Sortie |
|------------------------|--------|
| test.test@gmail.com    | true   |
| test.test@gmail        | false  |
| test.test@gmail.com.fr | true   |
| test.test.fr           | false  |
|                        | false  |

##### isStrong (Password)
| Entrée          | Sortie |
|-----------------|--------|
| Azerty1!        | true   |
| Azerty11        | false  |
| Azty1!          | false  |
| Azerty!!        | false  |
| azerty11!       | false  |
| AZERTY1!azerty! | true   |

### Cas pour les tests fonctionnels

| Email                | Password  | Role   | Resultat                                            | canAccessAdminArea() |
|----------------------|-----------|--------|-----------------------------------------------------|----------------------|
| test.test@gmail.com  | Azerty1!  | MEMBER | Instance de User                                    | false                |
| test.test@gmail.com  | Azerty1!  | ADMIN  | Instance de User                                    | true                 |
| sdfsdfsdf@sdfsdf     | Azerty1!  | MEMBER | IllegalArugmentException("email must be valid")     | /                    |
| sdfsdfsdf@sdfsdf.com | azerty1!  | MEMBER | IllegalArugmentException("password must be strong") | /                    |
| sdfsdfsdf@sdfsdf.com | AAzerty1  | MEMBER | IllegalArugmentException("password must be strong") | /                    |
| sdfsdfsdf@sdfsdf.com | Azertyyy! | MEMBER | IllegalArugmentException("password must be strong") | /                    |
| sdfsdfsdf@sdfsdf.com | Azty1!    | ADMIN  | IllegalArugmentException("password must be strong") | /                    |
| sdfsdfsdf@sdfsdf.com | Azerty1!  | null   | IllegalArugmentException("ole must not be null")    | /                    |


#### Ecrit par l'ia (Gémini 3 Pro)

| Catégorie     | Scénario               | Donnée d’entrée        | Résultat attendu                | Exception / Message |
| ------------- | ---------------------- | ---------------------- | ------------------------------- | ------------------- |
| Nominal       | Email standard         | `alice@test.com`       | Succès                          | -                   |
| Nominal       | Email avec points      | `bob.smith@company.io` | Succès                          | -                   |
| Limite        | Format minimal         | `a@b.c`                | Succès                          | -                   |
| Normalisation | Avec espaces           | `" user@test.com "`    | Succès (stocké `user@test.com`) | -                   |
| Erreur        | Null                   | `null`                 | Échec                           | email must be valid |
| Erreur        | Vide                   | `" "`                  | Échec                           | email must be valid |
| Erreur        | Pas de `@`             | `alice.com`            | Échec                           | email must be valid |
| Erreur        | Multiples `@`          | `alice@@test.com`      | Échec                           | email must be valid |
| Erreur        | Pas de point après `@` | `alice@test`           | Échec                           | email must be valid |
| Erreur        | Rien avant le `@`      | `@test.com`            | Échec                           | email must be valid |


| Catégorie | Scénario            | Donnée d’entrée | Résultat attendu | Exception / Message     |
| --------- | ------------------- | --------------- | ---------------- | ----------------------- |
| Nominal   | Mot de passe fort   | `P@ssw0rd24`    | Succès           | -                       |
| Limite    | Longueur exacte (8) | `Ab1!5678`      | Succès           | -                       |
| Erreur    | Trop court (<8)     | `Ab1!567`       | Échec            | password must be strong |
| Erreur    | Pas de majuscule    | `p@ssw0rd24`    | Échec            | password must be strong |
| Erreur    | Pas de chiffre      | `P@ssword!`     | Échec            | password must be strong |
| Erreur    | Pas de car. spécial | `Password24`    | Échec            | password must be strong |
| Erreur    | Null ou vide        | `null` ou `""`  | Échec            | password must be strong |
