package com.vita9.gamification.gamification.game;

import com.vita9.gamification.gamification.game.domain.LeaderBoardRow;
import com.vita9.gamification.gamification.game.repository.BadgeRepository;
import com.vita9.gamification.gamification.game.repository.ScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaderBoardServiceImpl implements LeaderBoardService{


    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;

    @Override
    public List<LeaderBoardRow> getCurrentLeaderBoard() {

        List<LeaderBoardRow> scores = scoreRepository.findFirst10();

        log.info("leaderboard "+scores);

        // combine them with badges
       return scores.stream().map(row -> {

           List<String> badges = badgeRepository.findByUserId(row.getUserId())
                   .stream()
                   .map(badgeCard -> badgeCard.getBadgeType().getDescription())
                   .collect(Collectors.toList());

            // get the clone of row with badges list
           return row.withBadges(badges);

        }).collect(Collectors.toList());

    }
}
