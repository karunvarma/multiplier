package com.vita9.gamification.gamification.game;


import com.vita9.gamification.gamification.payload.ChallengeSolvedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Slf4j
@Service
public class GameEventHandler {

    private final GameService gameService;

    /**
     * processAttempt uses AUTO ack(default) back to broker
     * In case of exception(we are raising configureRabbitListeners)
     * the message will not be requeued and it will be lost
     * @param challengeSolvedEvent
     */
    @RabbitListener(queues = "${amqp.queue.gamification}")
    public void processAttempt(final ChallengeSolvedEvent challengeSolvedEvent){
        log.info("challenge solved attempt is received");
        try{
            gameService.newAttemptFromUser(challengeSolvedEvent);
        }
        catch (Exception exception){
            log.info("Error while trying to process the attempt from the user");
            throw new AmqpRejectAndDontRequeueException(exception);
        }
    }

}
