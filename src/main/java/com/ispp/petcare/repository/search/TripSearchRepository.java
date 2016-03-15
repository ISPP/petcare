package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.Trip;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Trip entity.
 */
public interface TripSearchRepository extends ElasticsearchRepository<Trip, Long> {
}
