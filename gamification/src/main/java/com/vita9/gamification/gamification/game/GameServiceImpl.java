package com.vita9.gamification.gamification.game;

import com.vita9.gamification.gamification.game.badgeprovider.BadgeProvider;
import com.vita9.gamification.gamification.game.domain.BadgeCard;
import com.vita9.gamification.gamification.game.domain.BadgeType;
import com.vita9.gamification.gamification.game.domain.ScoreCard;
import com.vita9.gamification.gamification.game.repository.BadgeRepository;
import com.vita9.gamification.gamification.game.repository.ScoreRepository;
import com.vita9.gamification.gamification.payload.ChallengeSolvedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@Service
public class GameServiceImpl implements GameService{

    private final ScoreRepository scoreRepository;
    private final BadgeRepository badgeRepository;
    private final List<BadgeProvider> badgeProviderList;

    @Override
    public GameResult newAttemptFromUser(ChallengeSolvedEvent challenge) {
        if(challenge.isCorrect()){

            ScoreCard scoreCard = new ScoreCard(challenge.getAttemptId(),
                    challenge.getUserId());

            scoreRepository.save(scoreCard);

            log.info("User {} scored {} points for attempt id {}",
                    challenge.getUserAlias(), scoreCard.getScore(),
                    challenge.getAttemptId());

            // process for badges
            List<BadgeCard> badgeTypeList = processForBadges(challenge);

            // return the game result
            return new GameResult(scoreCard.getScore(),
                                badgeTypeList.stream()
                                        .map(BadgeCard::getBadgeType)
                                        .collect(Collectors.toList()));
        }
        else {
            log.info("Attempt id {} is not correct" + " user {} will not get the score",
                    challenge.getAttemptId(),
                    challenge.getUserAlias());
            return new GameResult(0, List.of());
        }
    }

    /**
     *TODO need to write unit tests for it
     * @param solvedDTO
     * @return
     */
    private List<BadgeCard> processForBadges(ChallengeSolvedEvent solvedDTO) {

        // get the total score of the user via DB query
        Optional<Integer> optionalTotalScore = scoreRepository.getTotalScoreForUser(solvedDTO.getUserId());
        if (optionalTotalScore.isEmpty()) return List.of();
        int totalScore = optionalTotalScore.get();

        // get the existing badges from the user
        List<BadgeCard> badgeCards = badgeRepository.findByUserId(solvedDTO.getUserId());

        // extract the badge existing badge types
        Set<BadgeType> alreadyGotBadges = badgeCards
                .stream()
                .map(BadgeCard::getBadgeType)
                .collect(Collectors.toSet());

        // instead of doing if/else we are using command pattern
        // to match the to match the required condition
        // there can be multiple matches so need to check all conditions
        List<BadgeCard> newBadgeCardList = badgeProviderList.stream()
                .filter(badgeProvider -> !alreadyGotBadges.contains(badgeProvider.getBadgeType()))
                .map(badgeProvider -> badgeProvider.provideBadge(totalScore,badgeCards))
                .filter(Optional::isPresent)
                .map(badgeType -> new BadgeCard(solvedDTO.getUserId(), badgeType.get()))
                        .collect(Collectors.toList());

        badgeRepository.saveAll(newBadgeCardList);

        return newBadgeCardList;
    }
}
