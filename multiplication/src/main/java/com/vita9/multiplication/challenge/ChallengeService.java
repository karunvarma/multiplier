package com.vita9.multiplication.challenge;

import java.util.List;

public interface ChallengeService {

    /**
     *  Verifies if an attempt comming from the controller layer is valid or not.
     */
    ChallengeAttempt    verifyAttempt (ChallengeAttemptDTO resultAttempt);

    /**
     *
     * @param alias name for the user
     * @return a list of last 10 {@link ChallengeAttempt}
     */
    List<ChallengeAttempt> getStatsForUser(String alias);
}
