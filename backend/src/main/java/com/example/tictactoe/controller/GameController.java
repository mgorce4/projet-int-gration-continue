package com.example.tictactoe.controller;

import com.example.tictactoe.model.GameMode;
import com.example.tictactoe.model.GameState;
import com.example.tictactoe.model.MoveRequest;
import com.example.tictactoe.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/state")
    public ResponseEntity<GameState> getGameState() {
        return ResponseEntity.ok(gameService.getGameState());
    }

    @PostMapping("/move")
    public ResponseEntity<?> makeMove(@RequestBody MoveRequest moveRequest) {
        try {
            GameState updatedState = gameService.makeMove(moveRequest.getRow(), moveRequest.getCol());
            return ResponseEntity.ok(updatedState);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<GameState> resetGame() {
        return ResponseEntity.ok(gameService.resetGame());
    }

    @PostMapping("/reset-scores")
    public ResponseEntity<GameState> resetScores() {
        return ResponseEntity.ok(gameService.resetScores());
    }

    @PostMapping("/mode")
    public ResponseEntity<GameState> setGameMode(@RequestParam GameMode mode) {
        return ResponseEntity.ok(gameService.setGameMode(mode));
    }
}
