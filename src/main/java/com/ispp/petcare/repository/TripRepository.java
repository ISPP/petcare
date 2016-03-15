package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Trip;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Trip entity.
 */
public interface TripRepository extends JpaRepository<Trip,Long> {

}
