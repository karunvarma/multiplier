package com.vita9.gamification.gamification.game.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * which holds the amount of score that a
 * user obtains for a given challenge attempt
 */


@NoArgsConstructor // hibernate requires it
@AllArgsConstructor
@Data
@Entity
public class ScoreCard {

    private static final int DEFAULT_SCORE = 10;

    @Id
    @GeneratedValue
    private Long cardId;
    private Long attemptId;
    private Long userId;
    private int score;

    // do we really need time ?

    public ScoreCard(Long attemptId, Long userId) {
        this(null,attemptId,userId,DEFAULT_SCORE);
    }
}
