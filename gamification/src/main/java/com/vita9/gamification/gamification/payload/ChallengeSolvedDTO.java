package com.vita9.gamification.gamification.payload;


import lombok.Value;

@Value
public class ChallengeSolvedDTO {
    long attemptId;
    long userId;
    int factorA;
    int factorB;
    boolean correct;
    String userAlias;
}
