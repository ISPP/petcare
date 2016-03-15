package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.PetShipper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PetShipper entity.
 */
public interface PetShipperSearchRepository extends ElasticsearchRepository<PetShipper, Long> {
}
