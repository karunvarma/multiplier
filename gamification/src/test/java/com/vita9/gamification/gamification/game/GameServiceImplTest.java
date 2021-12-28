package com.vita9.gamification.gamification.game;


import com.vita9.gamification.gamification.game.badgeprovider.BadgeProvider;
import com.vita9.gamification.gamification.game.domain.BadgeCard;
import com.vita9.gamification.gamification.game.domain.BadgeType;
import com.vita9.gamification.gamification.game.domain.ScoreCard;
import com.vita9.gamification.gamification.game.repository.BadgeRepository;
import com.vita9.gamification.gamification.game.repository.ScoreRepository;
import com.vita9.gamification.gamification.payload.ChallengeSolvedDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class GameServiceImplTest {


    @Mock
    private BadgeRepository badgeRepository;

    @Mock
    private ScoreRepository scoreRepository;

    @Mock
    private BadgeProvider badgeProvider;

    private GameService gameService;


    @BeforeEach
    public void setUp(){
        gameService = new GameServiceImpl(
                scoreRepository,
                badgeRepository,
                List.of(badgeProvider));
    }

    @Test
    public void processCorrectAttempt(){

        // given
        ChallengeSolvedDTO challengeSolvedDTO =
                new ChallengeSolvedDTO(10,10,1,1,true,"john");

        // construct the Scorecard
        ScoreCard scoreCard = new ScoreCard(challengeSolvedDTO.getAttemptId(),challengeSolvedDTO.getUserId());

        // get the total Score
        given(scoreRepository.getTotalScoreForUser(challengeSolvedDTO.getUserId()))
                .willReturn(Optional.of(30));

        var badgeCardList = List.of(new BadgeCard(challengeSolvedDTO.getUserId(), BadgeType.BRONZE));

        // existing badges
        given(badgeRepository.findByUserId(challengeSolvedDTO.getUserId()))
                .willReturn(badgeCardList);

        // we only have bronze badge provider
        given(badgeProvider.getBadgeType())
                .willReturn(BadgeType.BRONZE);

        given(badgeProvider.provideBadge(30, badgeCardList)).willReturn(Optional.of(BadgeType.BRONZE));


        // when
        final GameResult gameResult = gameService.newAttemptFromUser(challengeSolvedDTO);

        // then validate the result
        then(gameResult).isEqualTo(new GameResult(10,List.of(BadgeType.BRONZE)));

        // check if all the required calls are made or not.
        verify(scoreRepository).save(scoreCard);
        verify(badgeRepository).saveAll(List.of(new BadgeCard(challengeSolvedDTO.getUserId(),BadgeType.BRONZE)));
    }

    @Test
    public void processWrongAttempt(){
        // given
        ChallengeSolvedDTO challengeSolvedDTO =
                new ChallengeSolvedDTO(10,10,1,1,false,"john");
        // when
        GameResult gameResult = gameService.newAttemptFromUser(challengeSolvedDTO);

        // then should not score anything
        then(gameResult).isEqualTo(new GameResult(0, List.of()));
    }

}
