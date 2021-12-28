package com.vita9.gamification.gamification.game;


import com.vita9.gamification.gamification.game.domain.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {

    /**
     * @return It returns the sorted list of LeaderBoardRow objects
     */
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
