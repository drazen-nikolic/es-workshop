package com.seavus.workshop.elasticsearch.messaging.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_messages")
@Data
public class Message {

  @Id
  @GeneratedValue
  private Long id;

  private String username;

  private String text;

  private LocalDateTime time;

}
