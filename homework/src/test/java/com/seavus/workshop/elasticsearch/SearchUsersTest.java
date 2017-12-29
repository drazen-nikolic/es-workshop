package com.seavus.workshop.elasticsearch;

import com.seavus.workshop.elasticsearch.search.domain.IndexedMessage;
import com.seavus.workshop.elasticsearch.search.service.Search;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchUsersTest {
  @Autowired
  private Search search;

  @Test
  public void testSearchByUsername() {
    search.reindex();
    LocalDate from = LocalDate.of(2012, 5, 1);
    LocalDate to = LocalDate.of(2018, 5, 1);
    List<IndexedMessage> users = search.searchByUsername("laura peterson", convert(from), convert(to));
    users.forEach(System.out::println);
  }

  private Date convert(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
