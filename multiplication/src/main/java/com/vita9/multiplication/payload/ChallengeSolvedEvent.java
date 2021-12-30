package com.vita9.multiplication.payload;


import lombok.Value;

@Value
public class ChallengeSolvedEvent {
    long attemptId;
    long userId;
    int factorA;
    int factorB;
    boolean correct;
    String userAlias;
}
