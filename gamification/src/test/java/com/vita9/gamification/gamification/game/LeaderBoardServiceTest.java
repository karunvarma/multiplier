package com.vita9.gamification.gamification.game;


import com.vita9.gamification.gamification.game.domain.BadgeCard;
import com.vita9.gamification.gamification.game.domain.BadgeType;
import com.vita9.gamification.gamification.game.domain.LeaderBoardRow;
import com.vita9.gamification.gamification.game.repository.BadgeRepository;
import com.vita9.gamification.gamification.game.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class LeaderBoardServiceTest {

    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private BadgeRepository badgeRepository;

    private LeaderBoardService leaderBoardService;

    @BeforeEach
    public void setUp() {
        leaderBoardService = new LeaderBoardServiceImpl(
                scoreRepository, badgeRepository
        );
    }


    @Test
    public void retrieveLeaderBoardTest(){
        // given
        LeaderBoardRow leaderBoardRow = new LeaderBoardRow(1L,300L, List.of());
        BadgeCard badgeCard =  new BadgeCard(1L, BadgeType.BRONZE);

        given(scoreRepository.findFirst10()).willReturn(List.of(leaderBoardRow));
        given(badgeRepository.findByUserId(1L)).willReturn(List.of(badgeCard));

        // when
        List<LeaderBoardRow> actualList = leaderBoardService.getCurrentLeaderBoard();

        // then
        List<LeaderBoardRow> expectedList = List.of(
                new LeaderBoardRow(1L,300L,List.of(badgeCard.getBadgeType().getDescription())));

        then(actualList).isEqualTo(expectedList);
    }
}
