package com.vita9.gamification.gamification.game.repository;

import com.vita9.gamification.gamification.game.domain.LeaderBoardRow;
import com.vita9.gamification.gamification.game.domain.ScoreCard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Handles CRUD Operations related to score card.
 */
public interface ScoreRepository extends CrudRepository<ScoreCard,Long> {

    /**
     * Gets the total score for a given user: the sum of scores of all
     * scorecards
     * @param userId the id of the user
     * @return the total of the user, empty if no such user is present.
     */
    @Query("SELECT SUM(s.score) FROM ScoreCard s WHERE s.userId = :userId")
    Optional<Integer> getTotalScoreForUser(@Param("userId") long userId);


    /**
     * Retrieves the list of the {@Link LeaderBoardRow}
     * @return the top 10 rows, sorted by the scores in descending order.
     *
     * reference: https://www.baeldung.com/jpa-queries-custom-result-with-aggregation-functions#solution_constructor
     */
    @Query("SELECT new com.vita9.gamification.gamification.game.domain.LeaderBoardRow(s.userId,SUM(s.score)) " +
            " FROM ScoreCard s" +
            " GROUP BY s.userId" +
            " ORDER BY SUM(s.score) DESC")
    List<LeaderBoardRow> findFirst10();
}
