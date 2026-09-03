<template>
  <div class="controls-container">
    <div class="mode-selector glass-panel">
      <button
        class="mode-btn"
        :class="{ active: gameMode === 'TWO_PLAYERS' }"
        @click="$emit('change-mode', 'TWO_PLAYERS')"
      >
        👥 2 Joueurs
      </button>
      <button
        class="mode-btn"
        :class="{ active: gameMode === 'VS_AI' }"
        @click="$emit('change-mode', 'VS_AI')"
      >
        🤖 Contre l'IA
      </button>
    </div>

    <div class="actions">
      <button class="btn btn-primary" @click="$emit('reset-board')">
        🔄 Rejouer la manche
      </button>
      <button class="btn btn-secondary" @click="$emit('reset-scores')">
        🗑️ Scores à zéro
      </button>
    </div>
  </div>
</template>

<script setup>
defineProps({
  gameMode: {
    type: String,
    default: 'TWO_PLAYERS'
  }
})

defineEmits(['change-mode', 'reset-board', 'reset-scores'])
</script>

<style scoped>
.controls-container {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.mode-selector {
  display: flex;
  padding: 6px;
  gap: 6px;
}

.mode-btn {
  flex: 1;
  background: transparent;
  border: none;
  color: var(--text-muted);
  font-family: var(--font-body);
  font-size: 0.9rem;
  font-weight: 600;
  padding: 10px;
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all 0.3s ease;
}

.mode-btn:hover {
  color: var(--text-main);
}

.mode-btn.active {
  background: rgba(255, 255, 255, 0.1);
  color: var(--text-main);
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
}

.actions {
  display: flex;
  gap: 12px;
  justify-content: center;
}
</style>
