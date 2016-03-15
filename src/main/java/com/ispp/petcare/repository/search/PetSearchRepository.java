package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.Pet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Pet entity.
 */
public interface PetSearchRepository extends ElasticsearchRepository<Pet, Long> {
}
