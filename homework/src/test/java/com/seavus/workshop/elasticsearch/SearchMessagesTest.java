package com.seavus.workshop.elasticsearch;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import com.seavus.workshop.elasticsearch.search.domain.IndexedMessage;
import com.seavus.workshop.elasticsearch.search.service.Search;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchMessagesTest {
  @Autowired
  private Search search;

  @Before
  public void setup() {
    search.reindex();
  }

  @Test
  public void testSearch() {
    List<IndexedMessage> foundMessages = search.searchMessageTexts("fox");
    assertThat(foundMessages.size(), is(2));
  }
  @Test
  public void testSearchPhrase() {
    List<IndexedMessage> foundMessages = search.searchMessageTexts("my foxes");
//    assertThat(foundMessages.size(), is(2));
    assertThat(foundMessages.get(0).getText(), is("All my foxes are out on the street! :)"));
    foundMessages.forEach(System.out::println);
  }
}
