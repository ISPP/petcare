package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.Administrator;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Administrator entity.
 */
public interface AdministratorSearchRepository extends ElasticsearchRepository<Administrator, Long> {
}
