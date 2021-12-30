package com.vita9.gamification.gamification.game;


import com.vita9.gamification.gamification.payload.ChallengeSolvedEvent;


public interface GameService {

    /**
     * Process a new Attempt from the user.
     * @param challege it contains user details and the game result.
     * @return {@link  GameResult} object containing new score and
     * badge cards are obtained.
     */
    GameResult newAttemptFromUser(ChallengeSolvedEvent challege);

}

