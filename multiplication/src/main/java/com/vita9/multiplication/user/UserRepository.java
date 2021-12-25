package com.vita9.multiplication.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,String> {

    Optional<User> findByAlias(String alias);
}
