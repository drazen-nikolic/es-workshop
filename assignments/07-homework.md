# Homework

## Working with Elasticsearch via Java Client

The goal of this final workshop assignments is to make a simple application which utilizes
Elasticsearch and its features via Java Client.

The test project is based on [Spring Boot](https://projects.spring.io/spring-boot/) and 
[Spring Data Elasticsearch](https://projects.spring.io/spring-data-elasticsearch/).

*For more details about Spring Data project and how to use it with Elasticsearch, check
[Documentation](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/). Note that 
Chapter 1. "Working with Spring Data Repositories"is a general description about Spring Data
project abstraction, while [Chapter 2. "Elasticsearch Repositories"](https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#reference) 
is a reference documentation for Spring Data Elasticsearch project.*

## The Search Feature for a Company Messaging Portal

Your company has implemented an official company messaging portal, where colleagues can
login and send messages. All sent messages are stored in a relation database in a table 
`user_messages`

|field_name|type|
|--------|-------|
|id|long (PK)|
|username|varchar|
|message_text|text|
|time|timestamp|

Your task is to implement Full-text Search Feature for this company portal. 

### Requirements

1  
Users should be able to search messages by username as: give me all messages this user has sent
in the given period (date-from, date-to). Dates will be sent as text in a ISO 8601 format, like: "2017-12-25".

2
Users should be able to search `messages_text` by an arbitrary string - give me all messages containing 
a specific "search text" in a message text.   
The search should be "full-text" search and consider different word forms, synonyms etc.  
All messages are written in English.  

If there are multiple words in the "search text", match all messages containing at least one word. 
Additionally, messages containing the exact phrase should be positioned at the top of the search result.

#### Part 1 - Indexing of Messages

Implement the logic for reindexing the messages from the relational database into Elasticsearch.

Analyze data types and how each field should be mapped in Elasticsearch index.

Note also that the search requirements consider searching messages by time in a "resolution" of a single day,
so index time information accordingly. 

#### Part 2 - Search by Username and Date Period

Implement the logic for searching by given username and date period (date-from, date-to). 

Note that search should do an exact match on username.

#### Part 3 - Full-text Search Within Messages Text

Implement the logic which will execute the full-text search in `message_text` by the given search word 
or phrase (two or more words, separated by space character).

Note that search query should take care that messages having the exact search phrase are more relevant
than messages containing only a single word. 





