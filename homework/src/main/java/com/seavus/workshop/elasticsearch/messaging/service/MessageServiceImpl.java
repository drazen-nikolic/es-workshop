package com.seavus.workshop.elasticsearch.messaging.service;

import com.seavus.workshop.elasticsearch.messaging.domain.Message;
import com.seavus.workshop.elasticsearch.messaging.repository.MessageRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepository repository;

  @Override
  public List<Message> getAllMessages() {
    return repository.findAll();
  }
}
