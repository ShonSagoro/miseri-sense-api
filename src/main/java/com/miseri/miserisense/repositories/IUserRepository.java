package com.miseri.miserisense.repositories;

import com.miseri.miserisense.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends MongoRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
