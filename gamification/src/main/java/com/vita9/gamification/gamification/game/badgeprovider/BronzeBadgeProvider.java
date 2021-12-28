package com.vita9.gamification.gamification.game.badgeprovider;


import com.vita9.gamification.gamification.game.domain.BadgeCard;
import com.vita9.gamification.gamification.game.domain.BadgeType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class BronzeBadgeProvider implements BadgeProvider{

    @Override
    public Optional<BadgeType> provideBadge(int score, List<BadgeCard> existingList) {
        return score > 50 ?  Optional.of(getBadgeType()): Optional.empty();
    }

    @Override
    public BadgeType getBadgeType() {
        return BadgeType.BRONZE;
    }
}
