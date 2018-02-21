# Assignment 3

## Analyzers and Mappings

The goal of this assignment is to setup a new index - defining a proper index mapping
by using appropriate datatypes for the index fields as well as appropriate Analyzers
for the fields.

## Searching Blog Articles

The requirement is to add search capabilities to an existing blog website. 

`blog` contains `articles` with the next properties:
* `title` - Blog article title 
* `article_text` - Text of the blog article 
* `author_email` - Email address of the article author 
* `publish_date` - date when the article was/should be published (type: *date*)
* `article_id` - ID of the article (type: *long* number)
* `tags` - keywords related to a given article (comma-separated list of words)

`title` and `article_text` are written in English. The search requirements are next:
* `title` and `article_text` should be full-text searchable (the possibility 
to match the document by any word in the field)
* `author_email` should match only by complete email (complete phrase)
* `tags` - individual tags should be searched by phrase

The blog articles are stored in a relation database. You have to create an index
named `blog` (use index type `articles`) with the index fields as defined above. Datatypes 
used should best correspond to the "nature" of the each property data.


#### Assignment 3-1

In order to be able to index properly `tags` field (comma-separated values)
in your index define your custom tokenizer and analyzer. 

Name your custom analyzer as: `comma-delimited-analyzer`

*Hint: You can use "pattern tokenizer". See details on this 
[link](https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-pattern-tokenizer.html).*


**Expected result**: 

Execute this query:
```javascript
    POST blog/_analyze
    {
      "analyzer": "comma-delimited-analyzer",
      "text": "java,spring-boot,elasticsearch"
    }
```
You should get 3 tokens: `java`, `spring-boot` and `elasticsearch`

#### Assignment 3-2

Define field mappings as required.

*Hint: Use a help of [Example Mapping](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping.html#_example_mapping)
documentation from the Elastic website.*

*Hint 2: Since texts are in English, think of how this data should be indexed (analyzed),
so it can be better searchable.*

*Hint 3: When defining the types of textual properties, take into the consideration
which should be full-text searchable and which should be searchable by complete phrase only. 
See [Core Datatypes](https://www.elastic.co/guide/en/elasticsearch/reference/current/mapping-types.html#_core_datatypes)
from the official documentation.*

*Hint 4: For mapping creation use*: 
```javascript
PUT blog/_mapping/articles
{
  "properties" : {
    "field_name" : {
      "type" :    "field_type",
      "analyzer": "specific_analyzer-if-needed"
    },
    ...
  }
}
```

**Expected result**: 

If your index mapping query was correct, you should get this response:

```javascript
{
  "acknowledged": true,
  "shards_acknowledged": true,
  "index": "blog"
}
```

Once you create the index with the appropriate fields mapping:
1. execute `GET blog/articles/_mapping` - expected is to see the index mapping
as you've just created


#### Assignment 3-3

Index some data and validate it is correct indexed:
1. index blog articles by executing this pre-prepared query:
    ```javascript
    POST blog/articles/_bulk?refresh
    {"index":{"_id":1}}
    {"title": "Java 8 - New Date Time API", "article_text": "Date and Time API for Java, also known as JSR-310, provides a new and improved date and time API for Java", "author_email": "duke@java.com", "publish_date": "2017-10-15", "tags": "java", "article_id": 101}
    {"index":{"_id":2}}
    {"title": "Elasticsearch with Spring Data", "article_text": "The Spring Data Elasticsearch project applies core Spring concepts to the development of solutions using the Elasticsearch Search Engine.", "author_email": "springer@gmail.com", "publish_date": "2017-10-28", "tags": "java,spring-data,elasticsearch", "article_id": 102}
    {"index":{"_id":3}}
    {"title": "Aspects in Spring", "article_text": "One of the key components of Spring is the AOP framework. While the Spring IoC container does not depend on AOP, meaning you do not need to use AOP if you don't want to, AOP complements Spring IoC to provide a very capable middleware solution.", "author_email": "springer@gmail.com", "publish_date": "2017-11-05", "tags": "java,spring", "article_id": 103}
    {"index":{"_id":4}}
    {"title": "What is Kanban?", "article_text": "The Kanban method does not prescribe a certain setup or procedure. You can overlay Kanban properties on top of your existing workflow or process to bring your issues to light so that you can introduce positive change over time.", "author_email": "agile@example.com", "publish_date": "2017-12-19", "tags": "agile,kanban", "article_id": 104}
    {"index":{"_id":5}}
    {"title": "Refactoring to Functional", "article_text": "Moving to functional programming can result in significantly better code and productivity gains. However, it requires a paradigm shift: you need to move away from imperative and object-oriented thinking to start thinking functionally.", "author_email": "duke@java.com", "publish_date": "2017-12-25", "tags": "java", "article_id": 105}
    ```
    It is expected that indexing passes and in the response you see `"errors": false`
1. Execute `GET blog/articles/_search` - you should see there are *5 results*
1. Execute `GET blog/articles/_search?q=tags:java` - you should get *4 results*, 
1. Execute `GET blog/articles/_search?q=tags:spring-data` - you should get *1 result*, 
an article with the title "Elasticsearch with Spring Data"
1. Execute `GET blog/articles/_search?q=author_email:duke@java.com` - you should get *2 results*
1. Execute `GET blog/articles/_search?q=author_email:duke` - you should get *0 results*
1. Execute `GET blog/articles/_search?q=publish_date:>2017-12-01` -  you should get *2 results*,
with articles which publish date is in December 2017
1. Execute `GET blog/articles/_search?q=title:refactor` - you should get *1 result*
1. Execute `GET blog/articles/_search?q=article_text:(%2Bsolution+%2Belasticsearch)` - you should get *1 result*

