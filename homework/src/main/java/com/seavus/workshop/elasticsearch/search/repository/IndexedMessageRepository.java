package com.seavus.workshop.elasticsearch.search.repository;

import com.seavus.workshop.elasticsearch.search.domain.IndexedMessage;
import java.util.Date;
import java.util.List;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface IndexedMessageRepository extends ElasticsearchRepository<IndexedMessage, Long> {

}
