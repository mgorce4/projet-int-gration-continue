import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import ScoreBoard from '../ScoreBoard.vue'

describe('ScoreBoard.vue', () => {
  it('affiche les scores correctement', () => {
    const wrapper = mount(ScoreBoard, {
      props: {
        scoreX: 4,
        scoreO: 2,
        scoreDraws: 1,
        currentPlayer: 'X',
        gameMode: 'TWO_PLAYERS'
      }
    })

    expect(wrapper.text()).toContain('4')
    expect(wrapper.text()).toContain('2')
    expect(wrapper.text()).toContain('1')
  })

  it("met en évidence la carte du joueur actif X", () => {
    const wrapper = mount(ScoreBoard, {
      props: {
        currentPlayer: 'X',
        isGameOver: false
      }
    })

    const cardX = wrapper.find('.card-x')
    expect(cardX.classes()).toContain('active-turn')
  })
})
