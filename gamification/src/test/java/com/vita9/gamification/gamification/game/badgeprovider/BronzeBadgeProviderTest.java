package com.vita9.gamification.gamification.game.badgeprovider;

import com.vita9.gamification.gamification.game.domain.BadgeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class BronzeBadgeProviderTest {

    private BadgeProvider badgeProvider;

    @BeforeEach
    public void setUp(){
        badgeProvider = new BronzeBadgeProvider();
    }

    // positive
    @Test
    public void provideBadgeIfAboveThreshold(){
        // given
        int score = 51;
        // when
        Optional<BadgeType> badgeType =  badgeProvider.provideBadge(score, new ArrayList<>());
        // then
        assertThat(badgeType).contains(BadgeType.BRONZE);
    }


    // negative
    @Test
    public void doNotProvideBadgeIfBelowThreshold(){
        // given
        int score = 49;
        // when
        Optional<BadgeType> badgeType =  badgeProvider.provideBadge(score, new ArrayList<>());
        // then
        assertThat(badgeType).isEmpty();
    }
}
