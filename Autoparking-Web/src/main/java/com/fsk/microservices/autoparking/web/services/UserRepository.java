package com.fsk.microservices.autoparking.web.services;

import com.fsk.microservices.autoparking.web.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
