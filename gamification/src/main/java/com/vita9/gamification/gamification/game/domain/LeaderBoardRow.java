package com.vita9.gamification.gamification.game.domain;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.With;

import java.util.List;

/**
 * Not a DB entity
 */
@Value
@AllArgsConstructor
public class LeaderBoardRow {

    private Long userId;
    private Long totalScore;

    @With // to provide the clone setter
    List<String> badges;

    public LeaderBoardRow(Long userId, Long totalScore) {
        this.userId = userId;
        this.totalScore = totalScore;
        badges = List.of();
    }
}
