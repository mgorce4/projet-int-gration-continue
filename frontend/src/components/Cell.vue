<template>
  <button
    class="cell-btn"
    :class="{
      'cell-x': value === 'X',
      'cell-o': value === 'O',
      'cell-winning': isWinningCell,
      'disabled': disabled || !!value
    }"
    :disabled="disabled || !!value"
    @click="$emit('select-cell')"
    :aria-label="`Case ${row + 1}, ${col + 1} - ${value || 'Vide'}`"
  >
    <span v-if="value === 'X'" class="symbol symbol-x pop-in">X</span>
    <span v-else-if="value === 'O'" class="symbol symbol-o pop-in">O</span>
  </button>
</template>

<script setup>
defineProps({
  value: {
    type: String,
    default: ''
  },
  row: {
    type: Number,
    required: true
  },
  col: {
    type: Number,
    required: true
  },
  isWinningCell: {
    type: Boolean,
    default: false
  },
  disabled: {
    type: Boolean,
    default: false
  }
})

defineEmits(['select-cell'])
</script>

<style scoped>
.cell-btn {
  background: rgba(255, 255, 255, 0.03);
  border: 1px solid rgba(255, 255, 255, 0.08);
  border-radius: var(--radius-md);
  aspect-ratio: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: var(--font-heading);
  font-size: 2.8rem;
  font-weight: 900;
  cursor: pointer;
  transition: all 0.25s cubic-bezier(0.4, 0, 0.2, 1);
  outline: none;
  position: relative;
  overflow: hidden;
}

.cell-btn:not(.disabled):hover {
  background: rgba(255, 255, 255, 0.08);
  border-color: rgba(255, 255, 255, 0.2);
  transform: translateY(-2px) scale(1.02);
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.4);
}

.cell-x {
  border-color: rgba(0, 242, 254, 0.3);
}

.cell-o {
  border-color: rgba(255, 8, 68, 0.3);
}

.symbol-x {
  color: var(--color-x);
  text-shadow: 0 0 15px var(--color-x-glow);
}

.symbol-o {
  color: var(--color-o);
  text-shadow: 0 0 15px var(--color-o-glow);
}

.cell-winning {
  background: rgba(255, 255, 255, 0.12) !important;
  animation: pulseGlow 1.2s infinite alternate ease-in-out;
}

.cell-winning.cell-x {
  border-color: var(--color-x) !important;
  box-shadow: inset 0 0 20px var(--color-x-glow), 0 0 25px var(--color-x-glow);
}

.cell-winning.cell-o {
  border-color: var(--color-o) !important;
  box-shadow: inset 0 0 20px var(--color-o-glow), 0 0 25px var(--color-o-glow);
}
</style>
