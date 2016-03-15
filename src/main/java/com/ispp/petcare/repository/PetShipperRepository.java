package com.ispp.petcare.repository;

import com.ispp.petcare.domain.PetShipper;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PetShipper entity.
 */
public interface PetShipperRepository extends JpaRepository<PetShipper,Long> {

}
