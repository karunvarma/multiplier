package com.vita9.gamification.gamification.game.badgeprovider;


import com.vita9.gamification.gamification.game.domain.BadgeCard;
import com.vita9.gamification.gamification.game.domain.BadgeType;

import java.util.List;
import java.util.Optional;

/**
 * BadgeProvider is a command pattern
 */
public interface BadgeProvider {

    /**
     *  It decides whether a needs to be assigned
     *  by using few or all the passed parameters
     * @param score
     * @param existingList is a list of already provided badge cards
     * @return a BadgeType if user met the criteria, otherwise Empty
     */
    Optional<BadgeType> provideBadge(int score, List<BadgeCard> existingList);

    /**
     * @return the BadgeType the provider is handling
     */
    BadgeType getBadgeType();
 }
