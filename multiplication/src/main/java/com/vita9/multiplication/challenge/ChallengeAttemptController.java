package com.vita9.multiplication.challenge;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/attempts")
public class ChallengeAttemptController {

    private final ChallengeService challengeService;

    @PostMapping
    ResponseEntity<ChallengeAttempt> postAttempt(@RequestBody @Valid ChallengeAttemptDTO challengeAttemptDTO){
        return ResponseEntity.ok(challengeService.verifyAttempt(challengeAttemptDTO));
    }

    @GetMapping
    ResponseEntity<List<ChallengeAttempt>> getLastTenAttempts(@RequestParam("alias") String alias){
        return ResponseEntity.ok(challengeService.getStatsForUser(alias));
    }
}
