package com.example.tictactoe.service;

import com.example.tictactoe.entity.GameScoreEntity;
import com.example.tictactoe.model.GameMode;
import com.example.tictactoe.model.GameState;
import com.example.tictactoe.model.Player;
import com.example.tictactoe.repository.GameScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class GameServiceTest {

    private GameService gameService;
    private GameScoreRepository repositoryMock;

    @BeforeEach
    void setUp() {
        repositoryMock = Mockito.mock(GameScoreRepository.class);
        given(repositoryMock.findFirstByOrderByIdAsc()).willReturn(Optional.empty());
        gameService = new GameService(repositoryMock);
    }

    @Test
    @DisplayName("Devrait initialiser une nouvelle partie vide")
    void testInitialState() {
        GameState state = gameService.getGameState();
        assertNotNull(state);
        assertEquals(Player.X.name(), state.getCurrentPlayer());
        assertNull(state.getWinner());
        assertFalse(state.isDraw());
        assertFalse(state.isGameOver());
        assertEquals(0, state.getScoreX());
        assertEquals(0, state.getScoreO());

        String[][] board = state.getBoard();
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                assertTrue(board[r][c].isEmpty());
            }
        }
    }

    @Test
    @DisplayName("Devrait charger les scores depuis la BDD à l'initialisation")
    void testInitScoresFromDb() {
        GameScoreRepository mockRepo = Mockito.mock(GameScoreRepository.class);
        given(mockRepo.findFirstByOrderByIdAsc())
                .willReturn(Optional.of(new GameScoreEntity(5, 3, 2)));

        GameService serviceWithDb = new GameService(mockRepo);
        serviceWithDb.initScoresFromDb();

        GameState state = serviceWithDb.getGameState();
        assertEquals(5, state.getScoreX());
        assertEquals(3, state.getScoreO());
        assertEquals(2, state.getScoreDraws());
    }

    @Test
    @DisplayName("Devrait placer un coup valide et alterner le joueur")
    void testValidMove() {
        GameState state = gameService.makeMove(0, 0);
        assertEquals(Player.X.name(), state.getBoard()[0][0]);
        assertEquals(Player.O.name(), state.getCurrentPlayer());
    }

    @Test
    @DisplayName("Devrait rejeter un coup sur une case déjà occupée")
    void testOccupiedCellMove() {
        gameService.makeMove(0, 0);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.makeMove(0, 0);
        });
        assertEquals("Case déjà occupée.", exception.getMessage());
    }

    @Test
    @DisplayName("Devrait rejeter un coup hors limites")
    void testOutOfBoundsMove() {
        assertThrows(IllegalArgumentException.class, () -> gameService.makeMove(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> gameService.makeMove(3, 1));
    }

    @Test
    @DisplayName("Devrait détecter une victoire horizontale pour X et sauvegarder en BDD")
    void testHorizontalWinForX() {
        gameService.setGameMode(GameMode.TWO_PLAYERS);

        gameService.makeMove(0, 0); // X
        gameService.makeMove(1, 0); // O
        gameService.makeMove(0, 1); // X
        gameService.makeMove(1, 1); // O
        GameState state = gameService.makeMove(0, 2); // X

        assertTrue(state.isGameOver());
        assertEquals(Player.X.name(), state.getWinner());
        assertEquals(1, state.getScoreX());
        assertNotNull(state.getWinningLine());
        verify(repositoryMock).save(any(GameScoreEntity.class));
    }

    @Test
    @DisplayName("Devrait réinitialiser la partie et remettre à jour la BDD")
    void testResetScores() {
        gameService.makeMove(0, 0);
        GameState state = gameService.resetScores();

        assertTrue(state.getBoard()[0][0].isEmpty());
        assertEquals(0, state.getScoreX());
        assertEquals(0, state.getScoreO());
        assertEquals(0, state.getScoreDraws());
        verify(repositoryMock).save(any(GameScoreEntity.class));
    }
}
