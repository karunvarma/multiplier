package com.vita9.gamification.gamification.game;


import com.vita9.gamification.gamification.payload.ChallengeSolvedDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/attempts")
public class GameController {

    private final GameService gameService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    void postAttempt(@RequestBody ChallengeSolvedDTO challengeSolvedDTO){
            gameService.newAttemptFromUser(challengeSolvedDTO);
    }

}
