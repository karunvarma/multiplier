package com.vita9.multiplication.challenge;


import com.vita9.multiplication.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(ChallengeAttemptController.class)
class ChallengeAttemptControllerTest {


    @MockBean
    private ChallengeService challengeService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<ChallengeAttemptDTO> jsonRequestAttempt;
    @Autowired
    private JacksonTester<ChallengeAttempt> jsonResultAttempt;

    @Test
    void postvalidAttempt() throws Exception {

        // given

        // init all values
        User user = new User(1L,"karun");
        Long attemptId = 5L;
        ChallengeAttemptDTO attemptDTO =  new ChallengeAttemptDTO(50,50,"karun",2500);
        ChallengeAttempt expectedResponse = new ChallengeAttempt(attemptId,user,50,50,2500,true);

        // mocking the service call
        given(challengeService.verifyAttempt(eq(attemptDTO))).willReturn(expectedResponse);

        // when
        MockHttpServletResponse response = mvc.perform(post("/attempts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();

        System.out.println(response.getContentAsString());
        // then
        then(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(response.getContentAsString()).isEqualTo(jsonResultAttempt.write(expectedResponse).getJson());

    }

    @Test
    void postInvalidAttempt() throws Exception {

        // Given
        // we are getting some garbage data from the client
        ChallengeAttemptDTO attemptDTO =  new ChallengeAttemptDTO(-50,50,"karun",2500);

        // when
        MockHttpServletResponse response = mvc.perform(post("/attempts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequestAttempt.write(attemptDTO).getJson()))
                .andReturn().getResponse();
        // we are expecting a default response

        // then
        then(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
