package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.Complaint;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Complaint entity.
 */
public interface ComplaintSearchRepository extends ElasticsearchRepository<Complaint, Long> {
}
