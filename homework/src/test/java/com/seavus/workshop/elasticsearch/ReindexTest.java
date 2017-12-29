package com.seavus.workshop.elasticsearch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.seavus.workshop.elasticsearch.search.domain.IndexedMessage;
import com.seavus.workshop.elasticsearch.search.repository.IndexedMessageRepository;
import com.seavus.workshop.elasticsearch.search.service.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReindexTest {
  @Autowired
  private Search search;

  @Autowired
  private IndexedMessageRepository indexedMessageRepository;

  @Test
  public void testReindex() {
    search.reindex();
    assertThat(search.getAll().size(), is(1));

    // index arbitrary message - reindex should clear the index on reindexing
    IndexedMessage indexedMessage = new IndexedMessage();
    indexedMessage.setText("eee");
    indexedMessageRepository.save(indexedMessage);
    search.reindex();
    assertThat(search.getAll().size(), is(1));
  }
}
