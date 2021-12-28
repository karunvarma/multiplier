package com.vita9.multiplication.client;


import com.vita9.multiplication.challenge.ChallengeAttempt;
import com.vita9.multiplication.payload.ChallengeSolvedDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class GamificationServiceClient {

    private final RestTemplate restTemplate;
    private final String gamificationHostUrl;


    public GamificationServiceClient(final RestTemplateBuilder builder,
                                     @Value("${service.gamification.host}") final
                                     String gamificationHostUrl) {
        restTemplate = builder.build();
        this.gamificationHostUrl = gamificationHostUrl;
    }

    public boolean sendAttempt(final ChallengeAttempt challengeAttempt){

        try {
            ChallengeSolvedDTO challengeSolvedDTO =
                    new ChallengeSolvedDTO(
                            challengeAttempt.getId(),
                            challengeAttempt.getUser().getId(),
                            challengeAttempt.getFactorA(),
                            challengeAttempt.getFactorB(),
                            challengeAttempt.isCorrect(),
                            challengeAttempt.getUser().getAlias());
            ResponseEntity<String> response = restTemplate.postForEntity(
                    gamificationHostUrl + "/attempts",challengeSolvedDTO,String.class);
            log.info("Gamification service response {}",response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        }
        catch (Exception e){
            log.error("There was a problem in sending the attempt");
            return false;
        }



    }
}
