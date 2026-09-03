import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import GameControls from '../GameControls.vue'

describe('GameControls.vue', () => {
  it('affiche les boutons de mode et le bouton Scores à zéro', () => {
    const wrapper = mount(GameControls, {
      props: {
        gameMode: 'TWO_PLAYERS'
      }
    })

    expect(wrapper.text()).toContain('2 Joueurs')
    expect(wrapper.text()).toContain("Contre l'IA")
    expect(wrapper.text()).toContain('Scores à zéro')
  })

  it("émet l'événement change-mode lors du changement de mode", async () => {
    const wrapper = mount(GameControls, {
      props: {
        gameMode: 'TWO_PLAYERS'
      }
    })

    const aiButton = wrapper.findAll('.mode-btn')[1]
    await aiButton.trigger('click')

    expect(wrapper.emitted('change-mode')).toBeTruthy()
    expect(wrapper.emitted('change-mode')[0]).toEqual(['VS_AI'])
  })

  it("émet l'événement reset-board lors du clic sur Rejouer la manche", async () => {
    const wrapper = mount(GameControls)

    const resetBoardBtn = wrapper.find('.btn-primary')
    await resetBoardBtn.trigger('click')

    expect(wrapper.emitted('reset-board')).toBeTruthy()
  })

  it("émet l'événement reset-scores lors du clic sur Scores à zéro", async () => {
    const wrapper = mount(GameControls)

    const resetScoresBtn = wrapper.find('.btn-secondary')
    await resetScoresBtn.trigger('click')

    expect(wrapper.emitted('reset-scores')).toBeTruthy()
  })
})
