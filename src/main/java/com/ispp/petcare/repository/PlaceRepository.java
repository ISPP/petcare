package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Place;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Place entity.
 */
public interface PlaceRepository extends JpaRepository<Place,Long> {

}
