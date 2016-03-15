package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Administrator;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Administrator entity.
 */
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {

}
