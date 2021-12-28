package com.vita9.gamification.gamification.game;

import com.vita9.gamification.gamification.game.domain.BadgeType;
import lombok.Value;

import java.util.List;

@Value
class GameResult {
    int score;
    List<BadgeType> badgeTypeList;
}