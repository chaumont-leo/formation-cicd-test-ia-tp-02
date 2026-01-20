# Notes d'Utilisation de l'IA pour la Génération de Tests

Ce document récapitule l'usage de l'IA (Gemini) pour transformer des spécifications en code de test automatisé.

---

## 1. Outil IA utilisé

- **Modèle** : Gemini 3 Pro (Google)

---

## 2. Prompts utilisés

### Prompts améliorés et itérations

>Je souhaite que tu identifies, les règles métiers et que tu me donnes les cas nominaux, limites et d'erreurs afin de pouvoir ensuite les transformer en test unitaires.


- **Génération de code** :
  > Génère moi les cas de tests avec JUNIT 5  
  *(Conversion des tables en logique de test)*


- **Correction de bugs / Sécurité** :  
  Suite à l'erreur `CT_CONSTRUCTOR_THROW`, l'IA a été sollicitée pour que je puisse mieux comprendre l'erreur remonté et ensuite prendre la décision de transformer ça en factory.
  *(Finalizer Attack)*

---

## 3. Comparaison des tests (Humain vs IA)

| Aspect                  | Identification Humaine (Document PDF)  | Génération par l'IA (JUnit 5 / Regex)                                                                                                       |
|-------------------------|----------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------|
| Vitesse                 | Lente (lecture + rédaction manuelle)   | Instantanée                                                                                                                                 |
| Compréhension du besoin | Bonne compréhension global du besoin   | Rigoureuse dans ce contexte très bien définit et assez simple. Attention à l'esprit critique et la relecture sur des projets plus complexe. |
| Modularité              | Tableaux de scénarios avec les oracles | Génération de scénarios suivi des Tests paramétrés (`@ParameterizedTest`)                                                                   |
| Sécurité                | Non prise en compte initialement       | Identification et correction des vulnérabilités (Finalizer Attack)                                                                          |

---

## 4. Cas de tests proposés par l'IA

### Classification des scénarios

### A. Validation Email

- **Nominal** :
    - `alice@test.com`
    - `bob.smith@company.io`
- **Limite** :
    - `a@b.c` *(Format minimal)*
- **Erreur** :
    - `null`
    - chaîne vide
    - sans `@`
    - multiples `@`
    - pas de point après `@`

### B. Validation Mot de Passe

- **Nominal** :
    - `P@ssword24` *(Fort)*
- **Limite** :
    - `Ab1!5678` *(Longueur exacte de 8)*
- **Erreur** :
    - Trop court
    - Pas de majuscule
    - Pas de chiffre
    - Pas de caractère spécial

### C. Rôles et Autorisations

- **Nominal** :
    - `Role.ADMIN` → accès autorisé
    - `Role.MEMBER` → accès refusé
- **Erreur** :
    - rôle `null` → `IllegalArgumentException`

---

## 5. Analyse critique

### Tests conservés

- Tous les tests de validation de format (Email / Password), car ils correspondent strictement aux règles métier du document source.
- Les tests paramétrés pour les mots de passe faibles, car ils optimisent la couverture de code sans multiplier les méthodes.

### Tests rejetés ou modifiés

- **Appels directs au constructeur** :  
  Rejetés suite à l'alerte de sécurité `CT_CONSTRUCTOR_THROW`.
- **Décision humaine** :  
  Remplacement de l'instanciation directe par une **Static Factory Method** (`User.register()`).

### Décisions humaines majeures

1. **Immuabilité** :  
   Passage de la classe `User` en `final` pour bloquer les attaques par héritage.

2. **Gestion du `trim`** :  
   Application systématique de `.trim()` sur l'email avant validation, r
