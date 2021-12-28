package com.vita9.gamification.gamification.game.repository;

import com.vita9.gamification.gamification.game.domain.BadgeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Handles DB operations related to badges
 */
public interface BadgeRepository extends JpaRepository<BadgeCard,Long> {
    List<BadgeCard> findByUserId(long userId);
}
