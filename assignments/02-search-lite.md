# Assignment 2

## Querying using Search Lite

The goal of this assignment is to try indexing some data to Elasticsearch and to execute basic
queries on the data.

## Cocktail party

The idea behind this task is to simulate a good bartender, who can suggest cocktail ideas to the guests
in the bar, based on their preferences (what they like/dislike).

First, execute this [link](http://localhost:5601/app/kibana#/dev_tools/console?load_from=https:%2F%2Fraw.githubusercontent.com%2Fdrazen-nikolic%2Fes-workshop%2Fexamples%2Fassignments%2Fcocktails-bulk-create.txt)
to index some `cocktail`s into the `_drinks` index. The link should open your localhost Kibana installation
and `POST /_bulk` command with some data. Execute this command. As a result you should see something like this:

```javascript
{
  "took": 488,
  "errors": false,
  "items": [
    {
      "create": {
        "_index": "drinks",
        "_type": "cocktail",
        "_id": "1",
        "_version": 1,
        "result": "created",
        ...
```

To verify the next index got created and populated with some data, execute next (in Kibana Console):

`GET /drinks/cocktail/_search`

If everything went fine, you should see 10 hits (results) in the JSON result, like this:

```javascript
{
  "took": 12,
  "timed_out": false,
  "_shards": {
    "total": 5,
    "successful": 5,
    "skipped": 0,
    "failed": 0
  },
  "hits": {
    "total": 10,
    "max_score": 1,
    "hits": [
      {
        "_index": "drinks",
        "_type": "cocktail",
        "_id": "5",
        "_score": 1,
        "_source": {
          "name": "Margarita",
          "ingredients": "lime juice, orange liquer, blanco tequila, salt",
          "glass": "rocks",
          "strength": "medium",
          "garnish": "lime"
        }
      },
      {
        "_index": "drinks",
        "_type": "cocktail",
        "_id": "8",
        "_score": 1,
        "_source": {
          "name": "Blue Lagoon",
          "ingredients": "vodka, blue curacao liquer, lemonade",
          "glass": "regular",
          "strength": "medium",
          "garnish": "cherry"
        }
      },
      ...
```

### The Fields in the Index

The indexed documents has all fields of type `text` and their descriptions are as follows:
* **name** - cocktail name
* **ingredients** - list of ingredients needed to make a cocktail
* **glass** - type of glass in which the specific cocktail is served
* **strength** - how strong the drink is
* **garnish** - fruit, olives or similar, served with the drink

### Guests are arriving...

#### Assignment 2-1
Jane likes to drink *Cosmopolitan*. Tell her do you have this cocktail in your inventory.

*Hint: To search in an index (or index/type) you can use _search API - Search "lite" query. 
The query looks like this:*

    GET /drinks/cocktail/_search?q=<query> 
 
*... where `<query>` can be something like: `<field_name>:<value(s)>`*
 
**Expected result**: Yes, the Cosmopolitan is on the menu (only 1 result found).
 
 
#### Assignment 2-2
Vanessa had a bad day and is asking something containing *tequila*. You should 
tell offer her all cocktails containing *tequila* (within its `ingredients`).
 
**Expected result**: There are two cocktails: Margarita and Black Widow.
 
 
#### Assignment 2-3
James would like to drink something containing **orange** or **cranberry**.
  
*Hint: To match multiple values on a field, write them in brackets, separated by space.   
E.g. `?q=field:(value1 value2)`*
  
**Expected result**: There should be 4 cocktails found containing either orange or cranberry or both: 
Sex on the Beach, Dry Martini, Cosmopolitan and Margarita
  
   
#### Assignment 2-4
Elizabeth would like to drink something containing **vodka** but does not like **cranberry**.
 
*Hint: To specify that a field must have some value add plus (+) in front of the value. Analogous, 
to specify a value that the filed must not have add minus (-) in front of the value.   
E.g. `?q=field:(+must_have_value -must_NOT_have_value)`*
  
**Expected result**: There should be 3 cocktails found: 
Bloody Mary, Dirty Martini, Blue Lagoon


#### Assignment 2-5
Elizabeth has additional request. She would like to drink something containing **vodka** 
and does not contain **cranberry** (as in the previous assignment) but she prefers to have 
something also containing **lemonade**, if possible.

*Hint: Default operator - "should have" is specified in the query without + or - characters.*
  
**Expected result**: There should be still 3 cocktails found, as in the previous assignment: 
Bloody Mary, Dirty Martini, Blue Lagoon. But, the difference is the order of the returned results.
This time Blue Lagoon is at the top (it is the most relevant result, based on the given search criteria).


#### Assignment 2-6
Patricia successfully finished a project today and would like to have some cocktail with 
**whiskey** (ingredients) and a **cherry** on top (garnish).

*Hint: In order for a query to satisfy two must criterias (on a different fields)
you need to add in front of each filed name (in the query) a plus (+) character, 
marking it as a must criteria. Since plus character in the URL represents actually a blank character,
in this case you'll have to encoded it in the URL as `%2B`   
E.g. `?q=%2B<field_name1>:<value(s)> %2B<field_name2>:<value(s)>`*

**Expected result**: Only one result should be returned: Manhattan.


#### Assignment 2-7
Robert, who is not a guest but a bar owner (and you boss), asked you to add a new cocktail on the menu.

Hear are the details:   
* **name**: Grean Beast
* **ingredients**: absinthe, lime juice, syrup, water
* **glass**: regular
* **strength**: medium
* **garnish**: cucumber

*Hint: Use `POST` and specify the values as JSON document. `_id` will be auto-generated.*

**Expected result**: A new cocktail is indexed. Executing "and empty search" `GET /drinks/cocktail/_search`
there should be 11 hits in total.

*Note: In order to see all 11 results, you'll need to specify result size, as:   
`GET /drinks/cocktail/_search?size=11`*
