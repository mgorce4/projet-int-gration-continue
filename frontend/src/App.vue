<template>
  <main class="game-wrapper">
    <header class="header">
      <h1 class="title">MORPION <span class="glow-tag">NEO</span></h1>
      <p class="subtitle">Vue.js 3 & Spring Boot Backend</p>
    </header>

    <div v-if="error" class="error-banner">
      ⚠️ {{ error }}
    </div>

    <!-- Status Banner -->
    <div class="status-panel glass-panel" :class="{ 'status-winner': gameState.winner, 'status-draw': gameState.draw }">
      <div v-if="gameState.winner" class="status-content pop-in">
        🏆 Victoire du joueur <span :class="`symbol-${gameState.winner.toLowerCase()}`">{{ gameState.winner }}</span> !
      </div>
      <div v-else-if="gameState.draw" class="status-content pop-in">
        🤝 Égalité ! Match nul.
      </div>
      <div v-else class="status-content">
        Au tour du joueur <span :class="`symbol-${gameState.currentPlayer.toLowerCase()}`">{{ gameState.currentPlayer }}</span>
      </div>
    </div>

    <!-- ScoreBoard -->
    <ScoreBoard
      :score-x="gameState.scoreX"
      :score-o="gameState.scoreO"
      :score-draws="gameState.scoreDraws"
      :current-player="gameState.currentPlayer"
      :game-mode="gameState.gameMode"
      :is-game-over="gameState.gameOver"
    />

    <!-- Board -->
    <Board
      :board="gameState.board"
      :winning-line="gameState.winningLine"
      :is-game-over="gameState.gameOver"
      :disabled="loading"
      @make-move="handleMove"
    />

    <!-- Controls -->
    <GameControls
      :game-mode="gameState.gameMode"
      @change-mode="handleModeChange"
      @reset-board="handleResetBoard"
      @reset-scores="handleResetScores"
    />
  </main>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import ScoreBoard from './components/ScoreBoard.vue'
import Board from './components/Board.vue'
import GameControls from './components/GameControls.vue'
import {
  fetchGameState,
  sendMove,
  resetBoardApi,
  resetScoresApi,
  setGameModeApi
} from './services/api.js'

const gameState = ref({
  board: [['', '', ''], ['', '', ''], ['', '', '']],
  currentPlayer: 'X',
  winner: null,
  draw: false,
  gameOver: false,
  gameMode: 'TWO_PLAYERS',
  scoreX: 0,
  scoreO: 0,
  scoreDraws: 0,
  winningLine: null
})

const loading = ref(false)
const error = ref(null)

async function loadInitialState() {
  loading.value = true
  error.value = null
  try {
    const data = await fetchGameState()
    gameState.value = data
  } catch (err) {
    error.value = err.message || 'Impossible de se connecter au serveur backend.'
  } finally {
    loading.value = false
  }
}

async function handleMove({ row, col }) {
  if (gameState.value.gameOver || loading.value) return

  loading.value = true
  error.value = null
  try {
    const updatedState = await sendMove(row, col)
    gameState.value = updatedState
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}

async function handleResetBoard() {
  loading.value = true
  error.value = null
  try {
    const updatedState = await resetBoardApi()
    gameState.value = updatedState
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}

async function handleResetScores() {
  loading.value = true
  error.value = null
  try {
    const updatedState = await resetScoresApi()
    gameState.value = updatedState
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}

async function handleModeChange(mode) {
  loading.value = true
  error.value = null
  try {
    const updatedState = await setGameModeApi(mode)
    gameState.value = updatedState
  } catch (err) {
    error.value = err.message
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadInitialState()
})
</script>

<style scoped>
.game-wrapper {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.header {
  text-align: center;
  margin-bottom: 8px;
}

.title {
  font-size: 2.2rem;
  font-weight: 900;
  letter-spacing: 3px;
  background: linear-gradient(135deg, #ffffff 0%, #a5b4fc 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.glow-tag {
  color: var(--color-x);
  -webkit-text-fill-color: var(--color-x);
  text-shadow: 0 0 12px var(--color-x-glow);
}

.subtitle {
  font-size: 0.85rem;
  color: var(--text-muted);
  letter-spacing: 1px;
  margin-top: 4px;
}

.error-banner {
  background: rgba(255, 8, 68, 0.15);
  border: 1px solid var(--color-o);
  color: #ff8a9e;
  padding: 10px 16px;
  border-radius: var(--radius-md);
  font-size: 0.9rem;
  text-align: center;
}

.status-panel {
  padding: 14px;
  text-align: center;
  font-family: var(--font-heading);
  font-size: 1.1rem;
  font-weight: 700;
}

.symbol-x {
  color: var(--color-x);
  text-shadow: 0 0 8px var(--color-x-glow);
}

.symbol-o {
  color: var(--color-o);
  text-shadow: 0 0 8px var(--color-o-glow);
}

.status-winner {
  border-color: rgba(0, 242, 254, 0.5);
  box-shadow: 0 0 20px rgba(0, 242, 254, 0.2);
}

.status-draw {
  border-color: var(--accent-gold);
}
</style>
