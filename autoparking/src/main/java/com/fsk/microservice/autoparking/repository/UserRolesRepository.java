package com.fsk.microservice.autoparking.repository;

import com.fsk.microservice.autoparking.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

    List<UserRoles> findByUserId(Long userId);
}
