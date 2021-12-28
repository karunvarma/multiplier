package com.vita9.gamification.gamification.game.badgeprovider;

import com.vita9.gamification.gamification.game.domain.BadgeCard;
import com.vita9.gamification.gamification.game.domain.BadgeType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * TODO: unit tests are pending for this class
 */
@Component
public class FirstWonBadgeProvider implements BadgeProvider{
    @Override
    public Optional<BadgeType> provideBadge(int score, List<BadgeCard> existingList) {
        return existingList.size() == 0 ? Optional.of(getBadgeType()) : Optional.empty();
    }

    @Override
    public BadgeType getBadgeType() {
        return BadgeType.FIRST_WON;
    }
}
