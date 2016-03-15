package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Pet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pet entity.
 */
public interface PetRepository extends JpaRepository<Pet,Long> {

}
