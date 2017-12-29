package com.seavus.workshop.elasticsearch.messaging.service;

import com.seavus.workshop.elasticsearch.messaging.domain.Message;
import java.util.List;

/**
 * Services regarding messaging features.
 */
public interface MessageService {

  /**
   * Returns all messages from the data store.
   *
   * @return Result list.
   */
  List<Message> getAllMessages();
}
