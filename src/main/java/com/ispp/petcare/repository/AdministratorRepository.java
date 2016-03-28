package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Administrator;

import com.ispp.petcare.domain.Customer;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Administrator entity.
 */
public interface AdministratorRepository extends JpaRepository<Administrator,Long> {



    @Query("select a from Administrator a where a.user.id=?1")
    Administrator findAdministratorByUsername(Long id);
}
