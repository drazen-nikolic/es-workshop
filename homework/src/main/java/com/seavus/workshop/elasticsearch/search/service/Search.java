package com.seavus.workshop.elasticsearch.search.service;

import com.seavus.workshop.elasticsearch.search.domain.IndexedMessage;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Search features.
 */
public interface Search {

  /**
   * Reindex the complete messages index.
   */
  void reindex();

  /**
   * Returns all documents form the index.
   *
   * @return List of unordered {@link IndexedMessage}.
   */
  List<IndexedMessage> getAll();


  /**
   * Searches messages by username (exact match)
   * in the given period from-to dates, as specified.
   *
   * Note: All parameters are required and can not be null.
   *
   * @param username Username (author) who created the message. Case-insensitive.
   * @param dateFrom Search messages starting from this date.
   * @param dateTo Search messages starting up to this date.
   *
   * @return List of ordered {@link IndexedMessage}.
   */
  List<IndexedMessage> searchByUsername(String username, Date dateFrom, Date dateTo);

  /**
   * Searches messages by a search word or a phrase (full-text search).
   * Messages matching the exact phrase are more relevant and should appear at the top.
   *
   * @param searchString Search word or phrase
   * @return
   */
  List<IndexedMessage> searchMessageTexts(String searchString);
}
