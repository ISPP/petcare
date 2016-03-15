package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.Vehicle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Vehicle entity.
 */
public interface VehicleSearchRepository extends ElasticsearchRepository<Vehicle, Long> {
}
