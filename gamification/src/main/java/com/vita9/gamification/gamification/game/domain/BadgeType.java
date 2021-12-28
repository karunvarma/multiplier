package com.vita9.gamification.gamification.game.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Enumeration of different badge types a user can win
 */

@RequiredArgsConstructor
@Getter
public enum BadgeType {

    BRONZE("Bronze"),
    SILVER("Silver"),
    GOLD("Gold"),
    // Other badges won for different conditions
    FIRST_WON("First time"),
    LUCKY_NUMBER("Lucky number");

    // mapping enum constant to strig
    private final String description;
}
