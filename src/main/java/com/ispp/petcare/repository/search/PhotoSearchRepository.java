package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.Photo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Photo entity.
 */
public interface PhotoSearchRepository extends ElasticsearchRepository<Photo, Long> {
}
