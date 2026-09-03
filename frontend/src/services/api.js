const API_BASE_URL = '/api/game'

export async function fetchGameState() {
  const response = await fetch(`${API_BASE_URL}/state`)
  if (!response.ok) throw new Error('Impossible de charger l\'état du jeu.')
  return await response.json()
}

export async function sendMove(row, col) {
  const response = await fetch(`${API_BASE_URL}/move`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ row, col })
  })
  if (!response.ok) {
    const errData = await response.json()
    throw new Error(errData.error || 'Coup invalide')
  }
  return await response.json()
}

export async function resetBoardApi() {
  const response = await fetch(`${API_BASE_URL}/reset`, {
    method: 'POST'
  })
  if (!response.ok) throw new Error('Erreur lors de la réinitialisation.')
  return await response.json()
}

export async function resetScoresApi() {
  const response = await fetch(`${API_BASE_URL}/reset-scores`, {
    method: 'POST'
  })
  if (!response.ok) throw new Error('Erreur lors de la réinitialisation des scores.')
  return await response.json()
}

export async function setGameModeApi(mode) {
  const response = await fetch(`${API_BASE_URL}/mode?mode=${mode}`, {
    method: 'POST'
  })
  if (!response.ok) throw new Error('Erreur lors du changement de mode.')
  return await response.json()
}
