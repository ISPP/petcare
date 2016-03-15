package com.ispp.petcare.repository;

import com.ispp.petcare.domain.Complaint;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Complaint entity.
 */
public interface ComplaintRepository extends JpaRepository<Complaint,Long> {

}
