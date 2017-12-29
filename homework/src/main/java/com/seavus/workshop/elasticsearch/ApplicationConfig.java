package com.seavus.workshop.elasticsearch;

import com.seavus.workshop.elasticsearch.messaging.repository.MessageRepository;
import com.seavus.workshop.elasticsearch.messaging.service.MessageService;
import com.seavus.workshop.elasticsearch.messaging.service.MessageServiceImpl;
import com.seavus.workshop.elasticsearch.search.repository.IndexedMessageRepository;
import com.seavus.workshop.elasticsearch.search.service.Search;
import com.seavus.workshop.elasticsearch.search.service.SearchImpl;
import org.elasticsearch.node.NodeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Configuration
public class ApplicationConfig {

  @Bean
  public NodeBuilder nodeBuilder() {
    return new NodeBuilder();
  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate() {
    return new ElasticsearchTemplate(nodeBuilder().local(true).node().client());
  }

  @Bean
  public MessageService messageService(MessageRepository repository) {
    return new MessageServiceImpl(repository);
  }

  @Bean
  public Search search(MessageService messageService, IndexedMessageRepository indexedMessageRepository,
      ElasticsearchTemplate elasticsearchTemplate) {
    return new SearchImpl(messageService, indexedMessageRepository, elasticsearchTemplate);
  }
}
