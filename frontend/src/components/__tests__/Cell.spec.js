import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import Cell from '../Cell.vue'

describe('Cell.vue', () => {
  it("affiche le symbole X lorsque la valeur est X", () => {
    const wrapper = mount(Cell, {
      props: {
        value: 'X',
        row: 0,
        col: 0
      }
    })

    expect(wrapper.text()).toBe('X')
    expect(wrapper.find('.symbol-x').exists()).toBe(true)
  })

  it("émet l'événement select-cell lors du clic sur une case vide", async () => {
    const wrapper = mount(Cell, {
      props: {
        value: '',
        row: 1,
        col: 2
      }
    })

    await wrapper.trigger('click')
    expect(wrapper.emitted('select-cell')).toBeTruthy()
  })
})
