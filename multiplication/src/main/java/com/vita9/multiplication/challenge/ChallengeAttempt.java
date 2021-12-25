package com.vita9.multiplication.challenge;


import com.vita9.multiplication.user.User;
import lombok.*;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import javax.persistence.*;

/**
 * Identifies an attempt from a {@link User} to solve the challenge
 *
 * alternative decision:
 * we could have used the challenge id to get the factors
 */


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class ChallengeAttempt {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id") // user id will the name of the foreign key column in the Challenge attempt  table
    private User user;
    private int factorA;
    private int factorB;
    private int resultAttempt;
    private boolean correct;
}
