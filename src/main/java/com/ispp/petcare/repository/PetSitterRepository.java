package com.ispp.petcare.repository;

import com.ispp.petcare.domain.PetSitter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PetSitter entity.
 */
public interface PetSitterRepository extends JpaRepository<PetSitter,Long> {

}
