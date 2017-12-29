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
    indexRepository.deleteAll();
    List<Message> allMessages = messageService.getAllMessages();
    allMessages.stream()
        .map(this::createIndexedMessage)
        .forEach(indexRepository::save);
  }

  @Override
  public List<IndexedMessage> getAll() {
    return Lists.newArrayList(indexRepository.findAll());
  }

  @Override
  public List<IndexedMessage> searchByUsername(String username, Date dateFrom, Date dateTo) {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(boolQuery().must(matchQuery("username", username))
              .must(rangeQuery("date").from(dateFrom).to(dateTo)))
        .build();
    return elasticsearchTemplate.queryForList(searchQuery, IndexedMessage.class);
  }

  @Override
  public List<IndexedMessage> searchMessageTexts(String searchString) {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(boolQuery().must(matchQuery("text", searchString))
            .should(matchPhraseQuery("text", searchString))
        )
        .build();
    return elasticsearchTemplate.queryForList(searchQuery, IndexedMessage.class);
  }

  private IndexedMessage createIndexedMessage(Message message) {
    IndexedMessage indexedMessage = new IndexedMessage();
    indexedMessage.setId(message.getId());
    indexedMessage.setUsername(message.getUsername());
    indexedMessage.setText(message.getText());
    indexedMessage.setDate(convert(message.getTime()));
    return indexedMessage;
  }

  private Date convert(LocalDateTime localDateTime) {
    return Date.from(localDateTime.toLocalDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
