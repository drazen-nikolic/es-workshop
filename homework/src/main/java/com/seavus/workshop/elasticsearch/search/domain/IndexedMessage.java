package com.seavus.workshop.elasticsearch.search.domain;

import java.util.Date;
import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "companyportal", type = "messages", shards = 1, replicas = 0, refreshInterval = "-1")
@Data
public class IndexedMessage {
  @Id
  private Long id;

  // TODO-1 - populate proper @Field attributes
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String username;

  // TODO-2 - populate proper @Field attributes
  @Field(type = FieldType.String, index = FieldIndex.analyzed, analyzer = "english")
  private String text;

  // TODO-3 - populate proper @Field attributes
  @Field(type = FieldType.Date)
  private Date date;

}
