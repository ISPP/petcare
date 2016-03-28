package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Customer;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Customer entity.
 */
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("select a from Customer a where a.user.id=?1")
    Customer findCustomerByUserId(Long id);

}
