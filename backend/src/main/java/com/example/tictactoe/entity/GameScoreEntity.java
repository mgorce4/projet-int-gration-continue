package com.example.tictactoe.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "game_scores")
public class GameScoreEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int scoreX;
    private int scoreO;
    private int scoreDraws;

    private LocalDateTime updatedAt;

    public GameScoreEntity() {
    }

    public GameScoreEntity(int scoreX, int scoreO, int scoreDraws) {
        this.scoreX = scoreX;
        this.scoreO = scoreO;
        this.scoreDraws = scoreDraws;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
