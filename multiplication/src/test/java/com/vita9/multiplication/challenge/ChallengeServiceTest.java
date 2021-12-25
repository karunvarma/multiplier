package com.vita9.multiplication.challenge;

import com.vita9.multiplication.user.User;
import com.vita9.multiplication.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTest {
    private ChallengeService challengeService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChallengeAttemptRepository challengeAttemptRepository;

    @BeforeEach
    public void setUp(){

        challengeService = new ChallengeServiceImpl(userRepository,challengeAttemptRepository);


    }

    @Test
    public void checkCorrectAttemptTest(){
        // given
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50,60,"karun",3000);

        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());

        // when
        ChallengeAttempt result = challengeService.verifyAttempt(attemptDTO);

        // then
        then(result.isCorrect()).isTrue();

        // new user should be created
        verify(userRepository).save(new User("karun"));

        // the attempt should be stored
        verify(challengeAttemptRepository).save(result);
    }

    @Test
    public void checkExisitingUserTest(){

        // given
        User existingsUser = new User(1L,"karun");
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(30,60,"karun",3000);
        given(userRepository.findByAlias("karun")).willReturn(Optional.of(existingsUser));
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());

        // when
        ChallengeAttempt result = challengeService.verifyAttempt(attemptDTO);

        // then
        then(result.isCorrect()).isFalse();

        // existing user should be returned
        then(result.getUser()).isEqualTo(existingsUser);

        // the save method should not be called
        verify(userRepository,never()).save(any());

        // the attempt should be stored irrespective of the result
        verify(challengeAttemptRepository).save(result);

    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        given(challengeAttemptRepository.save(any())).will(returnsFirstArg());
        ChallengeAttemptDTO attemptDTO = new ChallengeAttemptDTO(50,60,"karun",1000);
        // when
        ChallengeAttempt result = challengeService.verifyAttempt(attemptDTO);

        // then
        then(result.isCorrect()).isFalse();
    }

    @Test
    public void retrieveStatsTest(){

        // given
        User existingsUser = new User(1L,"karun");

        ChallengeAttempt attempt1 = new ChallengeAttempt(1L, existingsUser, 50, 60, 3010, false);
        ChallengeAttempt attempt2 = new ChallengeAttempt(2L, existingsUser, 50, 60, 3051, false);
        List<ChallengeAttempt> lastAttempts = List.of(attempt1, attempt2);

        given(challengeAttemptRepository.findTop10ByUserAliasOrderByIdDesc(existingsUser.getAlias()))
                .willReturn(lastAttempts);

        // when
        List<ChallengeAttempt> challengeAttemptList = challengeService.getStatsForUser(existingsUser.getAlias());


        // then
        then(challengeAttemptList).isEqualTo(lastAttempts);

    }
}
