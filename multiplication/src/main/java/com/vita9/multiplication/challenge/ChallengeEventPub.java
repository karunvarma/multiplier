package com.vita9.multiplication.challenge;


import com.vita9.multiplication.payload.ChallengeSolvedEvent;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ChallengeEventPub {

    private final AmqpTemplate amqpTemplate;
    private final String challengeTopicExchange;

    public ChallengeEventPub(final AmqpTemplate amqpTemplate,
                             @Value("${amqp.exchange.attempts}")
                             final String challengesTopicExchange){
        this.amqpTemplate = amqpTemplate;
        this.challengeTopicExchange = challengesTopicExchange;
    }

    public void challengeSolved(final ChallengeAttempt challengeAttempt){

        ChallengeSolvedEvent challengeSolvedEvent = buildEvent(challengeAttempt);
        StringBuilder routingKeyBuilder = new StringBuilder();
        routingKeyBuilder.append("attempt.");
        if(challengeSolvedEvent.isCorrect()) routingKeyBuilder.append("correct");
        else routingKeyBuilder.append("wrong");

        amqpTemplate.convertAndSend(challengeTopicExchange, // exchange name
                                    routingKeyBuilder.toString(), // routing key
                                    challengeSolvedEvent); // message
    }

    private ChallengeSolvedEvent buildEvent(final ChallengeAttempt challengeAttempt){
         ChallengeSolvedEvent challengeSolvedEvent =
                new ChallengeSolvedEvent(
                        challengeAttempt.getId(),
                        challengeAttempt.getUser().getId(),
                        challengeAttempt.getFactorA(),
                        challengeAttempt.getFactorB(),
                        challengeAttempt.isCorrect(),
                        challengeAttempt.getUser().getAlias());

         return challengeSolvedEvent;
    }

}
