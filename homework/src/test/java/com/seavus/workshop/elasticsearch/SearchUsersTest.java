package com.seavus.workshop.elasticsearch;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

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
    List<IndexedMessage> users = search.searchByUsername("chandler", convert(from), convert(to));
    assertThat(users.size(), is(3));
    users.forEach(System.out::println);
  }

  @Test
  public void testSearchByUsernamePhrase() {
    String username = "laura peterson";
    search.reindex();
    LocalDate from = LocalDate.of(2012, 5, 1);
    LocalDate to = LocalDate.of(2018, 5, 1);
    List<IndexedMessage> users = search.searchByUsername(username, convert(from), convert(to));
    assertThat(users.size(), is(1));
    assertThat(users.get(0).getUsername(), is(username));
    users.forEach(System.out::println);
  }

  @Test
  public void testSearchByUsernameAndDatePeriod() {
    String username = "chandler";
    search.reindex();
    LocalDate from = LocalDate.of(2017, 9, 1);
    LocalDate to = LocalDate.of(2017, 9, 30);
    List<IndexedMessage> users = search.searchByUsername(username, convert(from), convert(to));
    assertThat(users.size(), is(2));
    users.forEach(u -> assertThat(u.getUsername(), is(username)));
    users.forEach(u -> assertThat(u.getDate(), is(greaterThan(convert(from)))));
    users.forEach(u -> assertThat(u.getDate(), is(lessThan(convert(to)))));
  }

  private Date convert(LocalDate localDate) {
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
  }
}
