package com.fsk.microservices.autoparking.web.services;

import com.fsk.microservices.autoparking.web.domain.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

    List<UserRoles> findByUserId(Long id);
}
