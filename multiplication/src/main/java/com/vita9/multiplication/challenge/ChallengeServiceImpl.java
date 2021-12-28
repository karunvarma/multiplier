package com.vita9.multiplication.challenge;

import com.vita9.multiplication.client.GamificationServiceClient;
import com.vita9.multiplication.user.User;
import com.vita9.multiplication.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    // we have DI in place.
    private final UserRepository userRepository;
    private final ChallengeAttemptRepository challengeAttemptRepository;
    private final GamificationServiceClient gamificationServiceClient;

    @Override
    public ChallengeAttempt verifyAttempt(ChallengeAttemptDTO attemptDTO) {


        // check if user already exists with that alias, otherwise create it
        User user = userRepository.findByAlias(attemptDTO.getUserAlias())
                .orElseGet( () -> {
                    log.info("Creating a user with alias {}",attemptDTO.getUserAlias());
                    return userRepository.save(new User(attemptDTO.getUserAlias()));
                });

        // check if attempt is correct or not
        boolean isCorrect = attemptDTO.getGuess() == attemptDTO.getFactorA() * attemptDTO.getFactorB();


        ChallengeAttempt challengeAttempt = new ChallengeAttempt(
                null,
                user,
                attemptDTO.getFactorA(),
                attemptDTO.getFactorB(),
                attemptDTO.getGuess(),
                isCorrect);

        // store the attempt
        ChallengeAttempt storedAttempt = challengeAttemptRepository.save(challengeAttempt);

        boolean status = gamificationServiceClient.sendAttempt(storedAttempt);

        log.info("Gamification service response {}",status);
        return storedAttempt;
    }



    @Override
    public List<ChallengeAttempt> getStatsForUser(String alias) {
        return challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(alias);
    }
}
