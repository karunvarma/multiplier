package com.vita9.multiplication.challenge;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/challenges")
@RequiredArgsConstructor
public class ChallengeController {

    @Autowired
    private final ChallengeGeneratorService challengeGeneratorService;


    @GetMapping("/random")
    Challenge getRandomChallege(){
        Challenge challenge = challengeGeneratorService.randomChallenge();
        log.info("Generating a random challenge : {}",challenge);
        return challenge;
    }

}
