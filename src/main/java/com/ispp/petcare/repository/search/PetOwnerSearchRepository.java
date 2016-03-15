package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.PetOwner;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PetOwner entity.
 */
public interface PetOwnerSearchRepository extends ElasticsearchRepository<PetOwner, Long> {
}
