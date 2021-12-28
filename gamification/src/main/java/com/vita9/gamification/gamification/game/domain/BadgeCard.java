package com.vita9.gamification.gamification.game.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Badge card object, representing a specific type of
 * badge that has been won at a given time by a user. It doesn’t need to
 * be tied to a scorecard since you may win a badge when you surpass a
 * given score threshold.
 *
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BadgeCard {

    @Id
    @GeneratedValue
    private Long badgeId;
    private Long userId;
    private BadgeType badgeType;

    // do we need time stamp ?
    // to track when the user won the badge.
    public BadgeCard(Long userId, BadgeType badgeType) {
        this(null,userId,badgeType);
    }
}
