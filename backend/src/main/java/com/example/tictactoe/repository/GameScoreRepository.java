package com.example.tictactoe.repository;

import com.example.tictactoe.entity.GameScoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameScoreRepository extends JpaRepository<GameScoreEntity, Long> {
    Optional<GameScoreEntity> findFirstByOrderByIdAsc();
}
