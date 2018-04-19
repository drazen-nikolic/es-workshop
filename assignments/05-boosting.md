# Assignment 5

## Tweaking Document Relevance

The goal of this assignment is to play with document relevance by tweaking queries.

## E-shop Merchandising

At a retail (shop) level, merchandising refers to the variety of products available 
for sale and the display of those products in such a way that it stimulates 
interest and entices customers to make a purchase.

You are a shop manager (with a background in software development). Since your ecommerce
software does not provide you tools to do some products promotions, you have chosen
to take control in your hands and tweak that search queries by yourself.

First, let's index some products. 

The index `shop` having a index type called `product` has next fields:
* `name` - product name
* `description` - product short description
* `brand` - product brand
* `category` - category of the product (like shoes, apparel etc.). 
    Each product has a single category.
* `inventory_amount` - amount of product you have available in your inventory

Execute next query to create new index with the mapping:

```javascript
PUT shop
{
  "mappings": {
    "product" : {
      "properties" : {
        "name" : {
          "type" :    "text",
          "analyzer": "english"
        },
        "description" : {
          "type" :    "text",
          "analyzer": "english"
        },
        "brand" : {
          "type" :   "keyword"
        },
        "category" : {
          "type" :   "keyword"
        },
        "inventory_amount" : {
          "type" :   "long"
        }
      }
    }
  }
}
```

Execute next `_bulk` query to index some products:

```javascript
POST /_bulk
{ "create": { "_index": "shop", "_type": "product", "_id" : "1" }}
{"name" : "Air Jordan 13 Retro", "description": "Men's Shoe", "brand":"Nike", "category":"shoes", "inventory_amount":"50"}
{ "create": { "_index": "shop", "_type": "product", "_id" : "2" }}
{"name" : "Nike Metcon 4 iD", "description": "Men's Training Shoe", "brand":"Nike", "category":"shoes", "inventory_amount":"5"}
{ "create": { "_index": "shop", "_type": "product", "_id" : "3" }}
{"name" : "Reebok crossfit Nano 2.0", "description": "WOMEN TRAINING", "brand":"Reebok", "category":"shoes", "inventory_amount":"150"}
{ "create": { "_index": "shop", "_type": "product", "_id" : "4" }}
{"name" : "Doom Sock Primeknit Trainers", "description": "Adidas Original Trainers", "brand":"Adidas", "category":"shoes", "inventory_amount":"15"}
{ "create": { "_index": "shop", "_type": "product", "_id" : "5" }}
{"name" : "Stretch denim jegging", "description": "Women classics", "brand":"Reebok", "category":"apparel", "inventory_amount":"3"}
```

Validate that the indexing passed OK by executing `GET shop/product/_search`.
You should get 5 hits.

#### Assignment 5-1

This week you have a promotion on the shoes of "Nike" `brand`.
Create the query which will return all the products from the `category` of "shoes" and
make Nike brand should to appear at the top of the results.

*Hint: Check this [link](https://www.elastic.co/guide/en/elasticsearch/guide/current/_boosting_query_clauses.html)
for an example of boosting query.*

**Expected result**: 4 product returned (category "shoes"). First two are Nike products.

#### Assignment 5-2

Your bosses told you to get rid of products which have a big inventory 
(a lot of items of the same product) since it takes too much space in the warehouse.
In the list of all product in the shop, promote those product which `inventory_amount`
is greather than 30.

*Hint: range query has a boosting property, e.g.:*
```javascript
"range": {
  "age": {
    "gte": 10,
    "lte": 20,
    "boost": 2
  }
}
```

**Expected result**: All 5 products returned in the result but the top 2 products have
`inventory_ammount` greather than 30.
