package com.example.tictactoe.controller;

import com.example.tictactoe.model.GameMode;
import com.example.tictactoe.model.GameState;
import com.example.tictactoe.model.MoveRequest;
import com.example.tictactoe.service.GameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/game/state - Devrait retourner l'état actuel de la partie")
    void testGetGameState() throws Exception {
        GameState mockState = new GameState();
        given(gameService.getGameState()).willReturn(mockState);

        mockMvc.perform(get("/api/game/state"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPlayer").value("X"))
                .andExpect(jsonPath("$.gameOver").value(false));
    }

    @Test
    @DisplayName("POST /api/game/move - Devrait accepter un coup valide")
    void testMakeMoveSuccess() throws Exception {
        GameState mockState = new GameState();
        mockState.getBoard()[0][0] = "X";
        mockState.setCurrentPlayer("O");

        given(gameService.makeMove(0, 0)).willReturn(mockState);

        MoveRequest moveRequest = new MoveRequest(0, 0);

        mockMvc.perform(post("/api/game/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.board[0][0]").value("X"))
                .andExpect(jsonPath("$.currentPlayer").value("O"));
    }

    @Test
    @DisplayName("POST /api/game/move - Devrait renvoyer 400 Bad Request pour un coup invalide")
    void testMakeMoveInvalid() throws Exception {
        given(gameService.makeMove(anyInt(), anyInt()))
                .willThrow(new IllegalArgumentException("Case déjà occupée."));

        MoveRequest moveRequest = new MoveRequest(0, 0);

        mockMvc.perform(post("/api/game/move")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(moveRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Case déjà occupée."));
    }

    @Test
    @DisplayName("POST /api/game/reset - Devrait réinitialiser la grille")
    void testResetGame() throws Exception {
        GameState mockState = new GameState();
        given(gameService.resetGame()).willReturn(mockState);

        mockMvc.perform(post("/api/game/reset"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPlayer").value("X"));
    }

    @Test
    @DisplayName("POST /api/game/reset-scores - Devrait réinitialiser les scores")
    void testResetScores() throws Exception {
        GameState mockState = new GameState();
        given(gameService.resetScores()).willReturn(mockState);

        mockMvc.perform(post("/api/game/reset-scores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scoreX").value(0))
                .andExpect(jsonPath("$.scoreO").value(0));
    }

    @Test
    @DisplayName("POST /api/game/mode - Devrait changer le mode de jeu")
    void testSetGameMode() throws Exception {
        GameState mockState = new GameState();
        mockState.setGameMode(GameMode.VS_AI);
        given(gameService.setGameMode(GameMode.VS_AI)).willReturn(mockState);

        mockMvc.perform(post("/api/game/mode?mode=VS_AI"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameMode").value("VS_AI"));
    }
}
