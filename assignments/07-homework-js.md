# Homework

## Working with Elasticsearch via Client Application (Javascript, .NET etc.)

The goal of this final workshop assignments is to make a simple application which utilizes
Elasticsearch and its features via client libraries for Javascript or .NET etc. 

In general, you will be working with the client API in the very similar way as directly accessing Elasticsearch's REST API. 

### Javascript Client

[elasticsearch.js](https://www.elastic.co/guide/en/elasticsearch/client/javascript-api/current/index.html) is API for accessing Elasticsearch cluster via Javascript. Visit the [Quick Start](https://www.elastic.co/guide/en/elasticsearch/client/javascript-api/current/quick-start.html) page to see all the basic operations. Basically, your queries will use Elasticsearch Query DSL. For indexing data and other available API methods check the API reference page (for your version of Elasticsearch), e.g. [6.2 API](https://www.elastic.co/guide/en/elasticsearch/client/javascript-api/current/api-reference.html).

### .NET Client

There are two .NET clients: *Elasticsearch.NET* (low-level API) and *NEST* (high-level) API. You can find the documentation [here](https://www.elastic.co/guide/en/elasticsearch/client/net-api/current/introduction.html). In general, it is recommended to use high-level client. 

**NEST** is a high level client that has the advantage of having mapped all the request and response objects, comes with a strongly typed query DSL that maps 1 to 1 with the Elasticsearch query DSL, and takes advantage of specific .NET features such as covariant results and auto mapping of POCOs. NEST internally uses and still exposes the low level Elasticsearch.Net client.

You can also check this [elasticsearch-net-example](https://github.com/elastic/elasticsearch-net-example/tree/5.x-codecomplete) tutorial.

### Other client libraries

For other languages check the list of [Elasticsearch Clients](https://www.elastic.co/guide/en/elasticsearch/client/index.html) APIs for e.g. PHP, Python, Ruby etc.

## The Search Feature for a Company Messaging Portal

Your company has implemented an official company messaging portal, where colleagues can login and send messages. All sent messages are stored in a relation database in a table `user_messages`

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
Users should be able to search `messages_text` by an arbitrary string - give me all messages containing a specific "search text" in a message text.   

The search should be "full-text" search and consider different word forms, synonyms etc.  

**All messages are written in English** (and will be searched by English speaking users).

If there are multiple words in the "search text", match all messages containing at least one word. Additionally, messages containing the exact phrase should be positioned at the top of the search result.

### Implementation

#### Part 1 - Create project skeleton

Analyze the documentation for your client library and create a simple project, which includes required Elasticsearch client API dependencies. 

For each functionality you are implementing, create few simple forms with buttons or some other way you would be able to trigger them (so you can later validate with "Expected results").

#### Part 2 - Implement relational database mock repository

Implement the logic for reindexing the messages from the relational database into Elasticsearch. 

In order not to complicate things too much, make a simple relational database mock repository, which returns all the messages from the database. Name this mock repository method as `findAllUserMessages()`, which will return an array of JSON objects, representing rows from the relational database table `user_messages`:

```javascript
[
{"id":1, "username":"chandler", "time":"2017-09-17 18:47:52.00", "message_text":"Hi there!"},
{"id":2, "username":"carmen", "time":"2017-09-17 18:47:52.00", "message_text":"I won't be able to make it. I'll be in a meeting"},
{"id":3, "username":"chandler", "time":"2017-09-17 18:47:52.00", "message_text":"I would like to add something great the **agenda**."},
{"id":4, "username":"mary", "time":"2017-09-17 18:47:52.00", "message_text":"The company party last night was the greatest so far :)"},
{"id":5, "username":"chandler", "time":"2017-10-20 10:15:22.00", "message_text":"Someone has problems with this new Fox reader?"},
{"id":6, "username":"carmen", "time":"2017-10-20 10:16:10.00", "message_text":"All my foxes are out on the street! :)"},
{"id":7, "username":"laura peterson", "time":"2017-09-17 18:47:52.00", "message_text":"this is my message!"},
{"id":8, "username":"user1", "time":"2017-09-17 18:47:52.00", "message_text":"My new fox"},
{"id":9, "username":"user2", "time":"2017-09-17 18:50:10.00", "message_text":"hello!"},
{"id":10, "username":"user1", "time":"2017-09-17 18:55:00.00", "message_text":"Hey there!"}
]
```

**Expected result**: When you call the mock repository method, as a result you will get back the JSON above.

#### Part 3 - Index mapping

Analyze data types and how each field should be mapped in Elasticsearch index. The fields in index should be named as:
* `id`
* `username`
* `date`
* `message_text`

Note also that the search requirements consider searching messages by time in a "resolution" of a single day, so index time information accordingly. 

By using the client library, create new index named `companyportal` and index type `messages`. Also, define the proper mapping for the Elasticsearch Messaging index (field names and types).

**Expected result**: When you execute this functionality (e.g. bounded to some button press), open Kibana Console and execute `GET companyportal/messages/_mapping`

You should get results back and see your defined index mapping.

*Hint: If you change index mapping, during this or some of the next steps, you should delete index first and then apply new mapping schema.*

#### Part 4 - Indexing of messages

Implement the logic for reindexing the messages from the relational database into Elasticsearch. This logic should call the mock repository method to obtain all the messages from the database and index them in Elasticsearch.

The method signature should be:

`reindex()`

**Expected result**: 
1. Call the reindex logic (e.g. click on a button), which will trigger the execution of `reindex()` method
2. Go to Kibana Console and execute: `GET companyportal/messages/_search` -> you should get 10 results back
3. In Kibana Console index another message:
    ```
	POST companyportal/messages
	{"message_text":"dummy message"}
	```
4. Execute `reindex()` again 
5. In Kibana Console execute again `GET companyportal/messages/_search` -> you should get 10 results back

#### Part 5 - Search by Username and Date Period

Implement the logic for searching by given username and date period (date-from, date-to). 

The method signature should be:

`searchByUsername(String username, Date dateFrom, Date dateTo)`

*Note that search should do an exact match on username.*


**Expected results**: 

Before executing the tests, make sure that the index is being created and populated (by the Reindex logic, from the previous step).

**Test 1**
1. Execute the logic with next parameters: `searchByUsername("chandler", "2012-05-01", "2018-05-01")`
2. It should return 3 results.

**Test 2**
1. Execute: `searchByUsername("laura peterson", "2012-05-01", "2018-05-01")`
2. It should return 1 result only.
3. `username` of the returned entity should be "laura peterson"

**Test 3**
1. Execute: `searchByUsername("chandler", "2017-09-01", "2017-09-30")`
2. It should return 2 results.
3. For each returned result:
* `username` should be "chandler"
* `date` value should be between "2017-09-01" and "2017-09-30"


#### Part 6 - Full-text Search Within Messages Text

Implement the logic which will execute the full-text search in `message_text` field by the given search word or phrase (two or more words, separated by space character).

The method signature should be:

`searchMessageTexts(String searchString)`

Note that search query should take care that messages having the exact search phrase are more relevant
than messages containing only a single word. 

*IMPORTANT*: Do not use wildcards (*) in a query.


**Expected result**: 

Before executing the tests, make sure that the index is being created and populated (by the Reindex logic).

**Test 1**
1. Execute: `searchMessageTexts("fox")`
2. There should be 3 documents in the result.

**Test 2**
1. Execute: `searchMessageTexts("my foxes")`
2. There should be 4 documents in the result.
3. The `message_text` of the first (top) result should be "All my foxes are out on the street! :)"
