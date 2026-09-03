<template>
  <div class="board-container glass-panel">
    <div class="grid">
      <template v-for="(rowArr, rIndex) in board" :key="rIndex">
        <Cell
          v-for="(val, cIndex) in rowArr"
          :key="`${rIndex}-${cIndex}`"
          :value="val"
          :row="rIndex"
          :col="cIndex"
          :is-winning-cell="checkWinningCell(rIndex, cIndex)"
          :disabled="disabled || isGameOver"
          @select-cell="$emit('make-move', { row: rIndex, col: cIndex })"
        />
      </template>
    </div>
  </div>
</template>

<script setup>
import Cell from './Cell.vue'

const props = defineProps({
  board: {
    type: Array,
    required: true
  },
  winningLine: {
    type: Array,
    default: null
  },
  isGameOver: {
    type: Boolean,
    default: false
  },
  disabled: {
    type: Boolean,
    default: false
  }
})

defineEmits(['make-move'])

function checkWinningCell(r, c) {
  if (!props.winningLine) return false
  return props.winningLine.some(([winR, winC]) => winR === r && winC === c)
}
</script>

<style scoped>
.board-container {
  padding: 20px;
  margin: 20px 0;
}

.grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 14px;
}
</style>
