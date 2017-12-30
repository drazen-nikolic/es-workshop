package com.seavus.workshop.elasticsearch.search.service;

import static org.elasticsearch.index.query.QueryBuilders.*;

import com.google.common.collect.Lists;
import com.seavus.workshop.elasticsearch.messaging.domain.Message;
import com.seavus.workshop.elasticsearch.messaging.service.MessageService;
import com.seavus.workshop.elasticsearch.search.domain.IndexedMessage;
import com.seavus.workshop.elasticsearch.search.repository.IndexedMessageRepository;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

@AllArgsConstructor
public class SearchImpl implements Search {

  private MessageService messageService;
  private IndexedMessageRepository indexRepository;
  private ElasticsearchTemplate elasticsearchTemplate;

  @Override
  public void reindex() {
    // TODO-4: Implement logic for reindexing data from relational database to Elasticsearch index

  }

  @Override
  public List<IndexedMessage> searchByUsername(String username, Date dateFrom, Date dateTo) {
    // TODO-5: By using NativeSearchQueryBuilder build the Elasticsearch query
    //         and get results via elasticsearchTemplate
    return null;
  }

  @Override
  public List<IndexedMessage> searchMessageTexts(String searchString) {
    // TODO-6: By using NativeSearchQueryBuilder build the Elasticsearch query
    //         to filter indexed messages as specified by the requirements
   return null;
  }

  @Override
  public List<IndexedMessage> getAll() {
    return Lists.newArrayList(indexRepository.findAll());
  }

  /**
   * Converts {@link LocalDateTime} to {@link Date}.
   *
   * @param localDateTime An instance of {@code java.time.LocalDateTime}.
   *
   * @return {@code java.util.Data} instance.
   */
  private Date convert(LocalDateTime localDateTime) {
    return Date.from(localDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
