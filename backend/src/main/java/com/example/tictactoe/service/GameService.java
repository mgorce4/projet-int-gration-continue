package com.example.tictactoe.service;

import com.example.tictactoe.entity.GameScoreEntity;
import com.example.tictactoe.model.GameMode;
import com.example.tictactoe.model.GameState;
import com.example.tictactoe.model.Player;
import com.example.tictactoe.repository.GameScoreRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {

    private final GameState gameState;
    private final GameScoreRepository gameScoreRepository;
    private final Random random = new Random();

    public GameService(GameScoreRepository gameScoreRepository) {
        this.gameState = new GameState();
        this.gameScoreRepository = gameScoreRepository;
    }

    @PostConstruct
    public void initScoresFromDb() {
        if (gameScoreRepository != null) {
            gameScoreRepository.findFirstByOrderByIdAsc().ifPresent(entity -> {
                this.gameState.setScoreX(entity.getScoreX());
                this.gameState.setScoreO(entity.getScoreO());
                this.gameState.setScoreDraws(entity.getScoreDraws());
            });
        }
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public GameState resetGame() {
        this.gameState.resetBoard();
        return this.gameState;
    }

    public GameState resetScores() {
        this.gameState.resetBoard();
        this.gameState.setScoreX(0);
        this.gameState.setScoreO(0);
        this.gameState.setScoreDraws(0);
        saveScoresToDb();
        return this.gameState;
    }

    public GameState setGameMode(GameMode mode) {
        this.gameState.setGameMode(mode);
        this.gameState.resetBoard();
        return this.gameState;
    }

    public GameState makeMove(int row, int col) {
        if (gameState.isGameOver()) {
            throw new IllegalArgumentException("La partie est déjà terminée.");
        }

        if (row < 0 || row > 2 || col < 0 || col > 2) {
            throw new IllegalArgumentException("Position invalide sur la grille (0-2).");
        }

        String[][] board = gameState.getBoard();
        if (!board[row][col].isEmpty()) {
            throw new IllegalArgumentException("Case déjà occupée.");
        }

        // Appliquer le coup du joueur actuel
        board[row][col] = gameState.getCurrentPlayer();

        // Vérifier victoire ou égalité
        if (checkGameStatus()) {
            saveScoresToDb();
            return gameState;
        }

        // Alterner le joueur
        switchTurn();

        // Si mode VS_AI et au tour de l'IA ('O')
        if (gameState.getGameMode() == GameMode.VS_AI 
                && Player.O.name().equals(gameState.getCurrentPlayer()) 
                && !gameState.isGameOver()) {
            makeAiMove();
            if (gameState.isGameOver()) {
                saveScoresToDb();
            }
        }

        return gameState;
    }

    private void saveScoresToDb() {
        if (gameScoreRepository == null) return;

        GameScoreEntity entity = gameScoreRepository.findFirstByOrderByIdAsc()
                .orElseGet(() -> new GameScoreEntity(0, 0, 0));

        entity.setScoreX(gameState.getScoreX());
        entity.setScoreO(gameState.getScoreO());
        entity.setScoreDraws(gameState.getScoreDraws());
        entity.setUpdatedAt(LocalDateTime.now());

        gameScoreRepository.save(entity);
    }

    private void switchTurn() {
        if (Player.X.name().equals(gameState.getCurrentPlayer())) {
            gameState.setCurrentPlayer(Player.O.name());
        } else {
            gameState.setCurrentPlayer(Player.X.name());
        }
    }

    private boolean checkGameStatus() {
        String[][] board = gameState.getBoard();
        int[][] winLine = findWinningLine(board);

        if (winLine != null) {
            String winnerSymbol = board[winLine[0][0]][winLine[0][1]];
            gameState.setWinner(winnerSymbol);
            gameState.setWinningLine(winLine);
            gameState.setGameOver(true);

            if (Player.X.name().equals(winnerSymbol)) {
                gameState.setScoreX(gameState.getScoreX() + 1);
            } else if (Player.O.name().equals(winnerSymbol)) {
                gameState.setScoreO(gameState.getScoreO() + 1);
            }
            return true;
        }

        if (isBoardFull(board)) {
            gameState.setDraw(true);
            gameState.setGameOver(true);
            gameState.setScoreDraws(gameState.getScoreDraws() + 1);
            return true;
        }

        return false;
    }

    public int[][] findWinningLine(String[][] board) {
        // Lignes
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].isEmpty() && board[r][0].equals(board[r][1]) && board[r][0].equals(board[r][2])) {
                return new int[][]{{r, 0}, {r, 1}, {r, 2}};
            }
        }

        // Colonnes
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].isEmpty() && board[0][c].equals(board[1][c]) && board[0][c].equals(board[2][c])) {
                return new int[][]{{0, c}, {1, c}, {2, c}};
            }
        }

        // Diagonale principale
        if (!board[0][0].isEmpty() && board[0][0].equals(board[1][1]) && board[0][0].equals(board[2][2])) {
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};
        }

        // Diagonale secondaire
        if (!board[0][2].isEmpty() && board[0][2].equals(board[1][1]) && board[0][2].equals(board[2][0])) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};
        }

        return null;
    }

    private boolean isBoardFull(String[][] board) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void makeAiMove() {
        String[][] board = gameState.getBoard();

        // 1. Chercher un coup gagnant pour 'O'
        int[] move = findBestWinningMove(board, Player.O.name());

        // 2. Chercher un coup pour bloquer 'X'
        if (move == null) {
            move = findBestWinningMove(board, Player.X.name());
        }

        // 3. Prendre le centre si libre
        if (move == null && board[1][1].isEmpty()) {
            move = new int[]{1, 1};
        }

        // 4. Prendre un coin au hasard
        if (move == null) {
            List<int[]> corners = new ArrayList<>();
            int[][] cornerCoords = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
            for (int[] coord : cornerCoords) {
                if (board[coord[0]][coord[1]].isEmpty()) {
                    corners.add(coord);
                }
            }
            if (!corners.isEmpty()) {
                move = corners.get(random.nextInt(corners.size()));
            }
        }

        // 5. Choisir n'importe quelle case vide
        if (move == null) {
            List<int[]> emptyCells = new ArrayList<>();
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    if (board[r][c].isEmpty()) {
                        emptyCells.add(new int[]{r, c});
                    }
                }
            }
            if (!emptyCells.isEmpty()) {
                move = emptyCells.get(random.nextInt(emptyCells.size()));
            }
        }

        if (move != null) {
            board[move[0]][move[1]] = Player.O.name();
            checkGameStatus();
            if (!gameState.isGameOver()) {
                switchTurn();
            }
        }
    }

    private int[] findBestWinningMove(String[][] board, String playerSymbol) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (board[r][c].isEmpty()) {
                    board[r][c] = playerSymbol;
                    boolean wins = findWinningLine(board) != null;
                    board[r][c] = ""; // backtrack
                    if (wins) {
                        return new int[]{r, c};
                    }
                }
            }
        }
        return null;
    }
}
