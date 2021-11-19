package com.fsk.microservice.autoparking.repository;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsk.microservice.autoparking.entity.Office;

@Repository
@Tag(name = "Office", description = "Office API")
public interface OfficeRepository extends JpaRepository<Office, Integer> {

}
