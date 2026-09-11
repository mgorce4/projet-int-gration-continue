/* Configuration ESLint minimale pour le projet Vue 3.
   Objectif pédagogique : intégrer l'outil d'analyse statique JS cité dans le
   cours (page 26 : "Analyse statique, dépendances : SonarQube, ESLint (JS)").
   Les règles restent volontairement proches des recommandations officielles
   Vue 3 pour ne pas provoquer un mur de violations sur un projet existant. */
module.exports = {
  root: true,
  env: {
    browser: true,
    es2021: true,
    node: true
  },
  extends: [
    'eslint:recommended',
    'plugin:vue/vue3-recommended'
  ],
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module'
  },
  rules: {
    'vue/multi-word-component-names': 'off',
    'no-unused-vars': 'warn'
  },
  ignorePatterns: ['dist/', 'node_modules/', 'coverage/']
}
