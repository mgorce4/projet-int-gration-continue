# 🎮 Jeu du Morpion (Tic-Tac-Toe Neo)

Projet complet de jeu de Morpion développé avec **Vue.js 3** (Frontend), **Java Spring Boot 3** (Backend), entièrement conteneurisé avec **Docker Compose** et couvert par des **tests unitaires**.

---

## 🚀 Fonctionnalités

- 🎨 **Interface Moderne**: Design Neo Glassmorphic sombre avec animations lumineuses pour 'X' et 'O'.
- 🎮 **Modes de Jeu**: Mode 2 Joueurs locaux ou Contre l'IA (IA avec détection de victoire/blocage).
- 🏆 **Gestion des Scores**: Suivi dynamique des victoires de X, O (ou IA) et des matchs nuls.
- ⚡ **Backend REST**: API RESTful Spring Boot réactive pour gérer les règles, l'état du jeu et les coups.
- 🧪 **Tests Unitaires Completes**:
  - **Backend**: Tests des services et controllers avec JUnit 5 & MockMvc.
  - **Frontend**: Tests unitaires des composants Vue 3 avec Vitest & Vue Test Utils.
- 🐳 **Conteneurisation Docker**: Multi-stage Dockerfiles pour minimiser la taille des images et Docker Compose pour orchestrer l'application.

---

## 🛠️ Structure du Projet

```
projet intégration continue/
├── docker-compose.yml           # Orchestration des conteneurs
├── README.md                    # Documentation du projet
├── backend/                     # Application Spring Boot (Java 21)
│   ├── Dockerfile               # Multi-stage build Maven -> JRE
│   ├── pom.xml                  # Configuration et dépendances Maven
│   └── src/
│       ├── main/java/           # Code source du backend
│       └── test/java/           # Tests unitaires JUnit 5
└── frontend/                    # Application Vue 3 + Vite
    ├── Dockerfile               # Multi-stage build Node -> Nginx
    ├── nginx.conf               # Serveur web & Proxy API
    ├── package.json             # Dépendances Node & scripts Vitest
    ├── vite.config.js           # Proxy dev & config test Vitest
    └── src/
        ├── components/          # Composants Vue (Board, Cell, ScoreBoard, GameControls)
        └── services/api.js      # Client d'intégration REST API
```

---

## 🧪 Exécution des Tests Unitaires

### 1. Tests Unitaires Backend (Spring Boot / JUnit 5)

Dans le dossier `backend` :
```bash
cd backend
mvn test
```

### 2. Tests Unitaires Frontend (Vue 3 / Vitest)

Dans le dossier `frontend` :
```bash
cd frontend
npm install
npm run test:unit
```

---

## 🐳 Lancement avec Docker Compose

L'ensemble de l'application (Backend + Frontend) peut être démarré en une seule commande grâce à Docker Compose.

À la racine du projet :
```bash
docker compose up --build -d
```

Une fois les conteneurs démarrés :
- 🌐 **Frontend (Application Web)** : [http://localhost:3000](http://localhost:3000)
- ⚙️ **Backend (API REST)** : [http://localhost:8080/api/game/state](http://localhost:8080/api/game/state)

Pour arrêter les conteneurs :
```bash
docker compose down
```

---

## 💻 Lancement en Mode Développement (Sans Docker)

### Backend Spring Boot
```bash
cd backend
mvn spring-boot:run
```
*(Le serveur démarre sur http://localhost:8080)*

### Frontend Vue 3
```bash
cd frontend
npm install
npm run dev
```
*(Accès sur http://localhost:3000 avec redirection proxy automatique vers 8080)*
