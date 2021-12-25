package com.vita9.multiplication.user;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * store user related information
 *
 * why do we ne empty constructor
 * https://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/Hibernate_User_Guide.html#entity-pojo-constructor
 *
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String alias;

    public User(String userAlias){
        this(null,userAlias);
    }
}
