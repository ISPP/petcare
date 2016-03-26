package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Complaint;

import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
 * Spring Data JPA repository for the Complaint entity.
 */
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {


    @Query("select c from Complaint c where c.customer.id=?1 and c.resolution is null")
    Collection<Complaint> findComplaintByCustommerIdAndNotResolution(Long id);


    @Query("select c from Complaint c where c.customer.id=?1 and c.resolution is not null")
    Collection<Complaint> findComplaintByCustommerIdAndResolution(Long id);


    @Query("select c from Complaint c where c.administrator is null")
    Collection<Complaint> findComplaintNotAdministrator();
}
