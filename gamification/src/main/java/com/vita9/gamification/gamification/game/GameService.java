package com.vita9.gamification.gamification.game;


import com.vita9.gamification.gamification.game.domain.BadgeType;
import com.vita9.gamification.gamification.payload.ChallengeSolvedDTO;
import lombok.Value;

import java.util.List;


public interface GameService {

    /**
     * Process a new Attempt from the user.
     * @param challege it contains user details and the game result.
     * @return {@link  GameResult} object containing new score and
     * badge cards are obtained.
     */
    GameResult newAttemptFromUser(ChallengeSolvedDTO challege);

}

