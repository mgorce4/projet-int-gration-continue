import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import Board from '../Board.vue'

describe('Board.vue', () => {
  const emptyBoard = [
    ['', '', ''],
    ['', '', ''],
    ['', '', '']
  ]

  it('rend correctement les 9 cases de la grille', () => {
    const wrapper = mount(Board, {
      props: {
        board: emptyBoard
      }
    })

    const cells = wrapper.findAllComponents({ name: 'Cell' })
    expect(cells.length).toBe(9)
  })

  it('émet un événement make-move lors du clic sur une case vide', async () => {
    const wrapper = mount(Board, {
      props: {
        board: emptyBoard
      }
    })

    const firstCell = wrapper.find('button')
    await firstCell.trigger('click')

    expect(wrapper.emitted('make-move')).toBeTruthy()
    expect(wrapper.emitted('make-move')[0]).toEqual([{ row: 0, col: 0 }])
  })

  it('met en surbrillance la ligne gagnante', () => {
    const winningBoard = [
      ['X', 'X', 'X'],
      ['O', 'O', ''],
      ['', '', '']
    ]

    const wrapper = mount(Board, {
      props: {
        board: winningBoard,
        winningLine: [[0, 0], [0, 1], [0, 2]],
        isGameOver: true
      }
    })

    const winningCells = wrapper.findAll('.cell-winning')
    expect(winningCells.length).toBe(3)
  })
})
