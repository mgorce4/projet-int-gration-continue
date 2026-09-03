package com.example.tictactoe.model;

public class GameState {
    private String[][] board;
    private String currentPlayer;
    private String winner;
    private boolean isDraw;
    private boolean isGameOver;
    private GameMode gameMode;
    private int scoreX;
    private int scoreO;
    private int scoreDraws;
    private int[][] winningLine; // e.g. [[0,0],[0,1],[0,2]] for highlighting winner cells

    public GameState() {
        this.board = new String[3][3];
        resetBoard();
        this.currentPlayer = Player.X.name();
        this.winner = null;
        this.isDraw = false;
        this.isGameOver = false;
        this.gameMode = GameMode.TWO_PLAYERS;
        this.scoreX = 0;
        this.scoreO = 0;
        this.scoreDraws = 0;
        this.winningLine = null;
    }

    public void resetBoard() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                this.board[r][c] = "";
            }
        }
        this.winner = null;
        this.isDraw = false;
        this.isGameOver = false;
        this.winningLine = null;
        this.currentPlayer = Player.X.name();
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public int getScoreX() {
        return scoreX;
    }

    public void setScoreX(int scoreX) {
        this.scoreX = scoreX;
    }

    public int getScoreO() {
        return scoreO;
    }

    public void setScoreO(int scoreO) {
        this.scoreO = scoreO;
    }

    public int getScoreDraws() {
        return scoreDraws;
    }

    public void setScoreDraws(int scoreDraws) {
        this.scoreDraws = scoreDraws;
    }

    public int[][] getWinningLine() {
        return winningLine;
    }

    public void setWinningLine(int[][] winningLine) {
        this.winningLine = winningLine;
    }
}
