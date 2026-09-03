<template>
  <div class="scoreboard glass-panel">
    <div class="score-card card-x" :class="{ 'active-turn': currentPlayer === 'X' && !isGameOver }">
      <span class="label">Joueur X</span>
      <span class="score">{{ scoreX }}</span>
    </div>

    <div class="score-card card-draws">
      <span class="label">Nuls</span>
      <span class="score">{{ scoreDraws }}</span>
    </div>

    <div class="score-card card-o" :class="{ 'active-turn': currentPlayer === 'O' && !isGameOver }">
      <span class="label">{{ gameMode === 'VS_AI' ? 'IA (O)' : 'Joueur O' }}</span>
      <span class="score">{{ scoreO }}</span>
    </div>
  </div>
</template>

<script setup>
defineProps({
  scoreX: {
    type: Number,
    default: 0
  },
  scoreO: {
    type: Number,
    default: 0
  },
  scoreDraws: {
    type: Number,
    default: 0
  },
  currentPlayer: {
    type: String,
    default: 'X'
  },
  gameMode: {
    type: String,
    default: 'TWO_PLAYERS'
  },
  isGameOver: {
    type: Boolean,
    default: false
  }
})
</script>

<style scoped>
.scoreboard {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 12px;
  padding: 14px;
}

.score-card {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border-radius: var(--radius-md);
  background: rgba(255, 255, 255, 0.02);
  border: 1px solid transparent;
  transition: all 0.3s ease;
}

.label {
  font-size: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: var(--text-muted);
  margin-bottom: 4px;
  font-weight: 600;
}

.score {
  font-family: var(--font-heading);
  font-size: 1.6rem;
  font-weight: 800;
}

.card-x .score {
  color: var(--color-x);
}

.card-o .score {
  color: var(--color-o);
}

.card-draws .score {
  color: var(--accent-gold);
}

.active-turn.card-x {
  border-color: var(--color-x);
  box-shadow: 0 0 12px var(--color-x-glow);
  background: rgba(0, 242, 254, 0.06);
}

.active-turn.card-o {
  border-color: var(--color-o);
  box-shadow: 0 0 12px var(--color-o-glow);
  background: rgba(255, 8, 68, 0.06);
}
</style>
