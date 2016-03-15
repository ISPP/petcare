package com.ispp.petcare.repository;

import com.ispp.petcare.domain.PetOwner;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PetOwner entity.
 */
public interface PetOwnerRepository extends JpaRepository<PetOwner,Long> {

}
