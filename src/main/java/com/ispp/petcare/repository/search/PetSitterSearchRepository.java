package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.PetSitter;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PetSitter entity.
 */
public interface PetSitterSearchRepository extends ElasticsearchRepository<PetSitter, Long> {
}
