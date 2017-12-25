# Assignment 4

## Query DSL

The goal of this assignment is to get familiar with Elasticsearch Query DSL.

## Searching Tweets

Withing many tweets, implement various queries to monitor - watch what people
are tweeting about. 
 
 `tweets` index (having simple index type called `entries`) has next fields:
 * `author` (e.g. @drazennis)
 * `tweet_text` (e.g. This is my new tweet!)
 * `tweet_date` - date when the tweet was created

First create new index and define mapping, by executing this query:
```javascript
PUT tweets
{
  "mappings": {
    "entries" : {
      "properties" : {
        "tweet_text" : {
          "type" :    "text",
          "analyzer": "english"
        },
        "tweet_date" : {
          "type" :   "date"
        },
        "author" : {
          "type" :   "keyword"
        }
      }
    }
  }
}
```

Then index some data by executing this bulk query:
```javascript
POST /_bulk
{ "create": { "_index": "tweets", "_type": "entries", "_id" : "8" }}
{"author" : "@venkat_s", "tweet_text": "The best way to make sure no one ever hates what you do, or how you do, is to never exist. Well, that's too late now... so do the best you can for those who truly will benefit from your sincere efforts, and ignore the rest.", "tweet_date":"2017-12-24"}
{ "create": { "_index": "tweets", "_type": "entries", "_id" : "7" }}
{"author" : "@venkat_s", "tweet_text": "Laziness is the Ultimate Sophistication, both in Life and in Programming", "tweet_date":"2017-12-19"}
{ "create": { "_index": "tweets", "_type": "entries", "_id" : "6" }}
{"author" : "@JohnCleese", "tweet_text": "Definition of an English gentleman : Someone who is never rude by accident", "tweet_date":"2017-12-10"}
{ "create": { "_index": "tweets", "_type": "entries", "_id" : "5" }}
{"author" : "@arandjel99", "tweet_text": "Dear Martha, I’we been developing things in Java since version 1.1, now my younger son is writing Arduino code in C and older one solves problems in C++. Have I failed as a parent? What is next? My wife asking me to install .NET environment?", "tweet_date":"2017-12-03"}
{ "create": { "_index": "tweets", "_type": "entries", "_id" : "4" }}
{"author" : "@inspire_us", "tweet_text": "Don't sit back and let things happen to you. Go out and happen to things.", "tweet_date":"2016-06-27"}
{ "create": { "_index": "tweets", "_type": "entries", "_id" : "3" }}
{"author" : "@inspire_us", "tweet_text": "Remind yourself that you don't have to do what everyone else is doing.", "tweet_date":"2016-03-27"}
{ "create": { "_index": "tweets", "_type": "entries", "_id" : "2" }}
{"author" : "@speakjava", "tweet_text": "#JDK11 will remove the Java garbage collector! http://openjdk.java.net/jeps/318 . Well, not quite, but the Epsilon collector will allow you to stop all GC if you're really, really sure you don't need it.", "tweet_date":"2017-12-01"}
{ "create": { "_index": "tweets", "_type": "entries", "_id" : "1" }}
{"author" : "@darilginn", "tweet_text": "Dear every website, No we do not want to enable push notifications. Ever. Sincerely, Everyone", "tweet_date":"2017-12-16"}
```

#### Assignment 4-1

Fetch all tweets from author @venkat_s.

*Note: Use Query DSL.*

**Expected result**: 2 found tweets, from an author @venkat_s


#### Assignment 4-2

Find tweets containing either of these words: *java* *website*

**Expected result**: 3 found tweets


#### Assignment 4-3

By using `bool` query, find tweets satisfying next criteria:
* the `tweet_text` must contain *java*
* the `tweet_text` must NOT contain word *aurduino*

**Expected result**: 3 found tweets


#### Assignment 4-4

By using a *range* query, find all tweets from December 2017.

*Hint: When matching dates use date ISO format as yyyy-mm-dd and put the date 
value within quotes (").*

**Expected result**: 6 found tweets


#### Assignment 4-5

Create a query to match all documents in `tweets` index and return only 2 results.

**Expected result**: You should see that there are total of 8 result found, but
only 2 tweets should be in result.


#### Assignment 4-6

Create `bool` query to match all tweets having a word *thing* but first filter
the tweets from year 2016.

**Expected result**: 1 tweet found.
