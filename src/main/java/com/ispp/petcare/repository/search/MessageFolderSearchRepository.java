package com.ispp.petcare.repository.search;

import com.ispp.petcare.domain.MessageFolder;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MessageFolder entity.
 */
public interface MessageFolderSearchRepository extends ElasticsearchRepository<MessageFolder, Long> {
}
