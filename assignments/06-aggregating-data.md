# Assignment 6

## Data Aggregation

The goal of this assignment is to tryout Elasticsearch aggregation features.

## Bank Accounts Reporting 

As an IT guy working in a bank, except restarting the servers from time to time, reinstalling Windows
on workstations and explaining that guy for the 5th time how to use a shared folder, at the end of the year
you get some additional tasks to generate different reports for the upper management.

The bank would like to start a new credit loan campaign but they are unsure which age group 
they should target. In other words, the age group having lower average account balance is more
likely to need to get a loan.

Here is some data to work with. Please, execute the _bulk query to index the data.

```javascript
POST future-bank/accounts/_bulk
{ "index": {}}
{ "name" : "John", "age" : 21, "balance" : 150 }
{ "index": {}}
{ "name" : "Virginia", "age" : 39, "balance" : 40540 }
{ "index": {}}
{ "name" : "Aurelia", "age" : 37, "balance" : 20150 }
{ "index": {}}
{ "name" : "Ratliff", "age" : 25, "balance" : 5400 }
{ "index": {}}
{ "name" : "Laverne", "age" : 60, "balance" : 90540 }
{ "index": {}}
{ "name" : "Effie", "age" : 45, "balance" : 3600 }
{ "index": {}}
{ "name" : "Rowena", "age" : 19, "balance" : 25 }
{ "index": {}}
{ "name" : "Hudson", "age" : 26, "balance" : 11870 }
{ "index": {}}
{ "name" : "Blake", "age" : 51, "balance" : 340 }
{ "index": {}}
{ "name" : "Garcia", "age" : 31, "balance" : 7600 }
{ "index": {}}
{ "name" : "Rachelle", "age" : 23, "balance" : 9100 }
{ "index": {}}
{ "name" : "Melissa", "age" : 42, "balance" : 19000 }
{ "index": {}}
{ "name" : "Marion", "age" : 64, "balance" : 23350 }
{ "index": {}}
{ "name" : "Lydia", "age" : 35, "balance" : 65420 }
{ "index": {}}
{ "name" : "Rosalinda", "age" : 28, "balance" : 4580 }
{ "index": {}}
{ "name" : "Leonard", "age" : 56, "balance" : 22540 }
```

#### Assignment 6-1

Divide the accounts by age groups and find the count of accounts in each age group.

Age groups definition:
* age <= 30
* 30 < age <= 40
* 40 < age <= 50
* age > 50

*Hint: Check [Range Aggregators](https://www.elastic.co/guide/en/elasticsearch/reference/current/search-aggregations-bucket-range-aggregation.html).*

*Hint 2: Aggregation values can be found at the bottom of the result JSON. 
If you are not interested in the results but just in aggregation values, add `"size" : 0` 
to the search request.*

**Expected result**: 

|age group|doc_count|
|---------|---------|
|*-30|6|
|30-40|2|
|40-50|3|
|50-*|4|


#### Assignment 6-2

With the defined age groups, add a nested metrics to find an average account balance for each age group.

*Hint: Check this [link](https://www.elastic.co/guide/en/elasticsearch/guide/current/_adding_a_metric_to_the_mix.html)
for help.*

**Expected result**:  

|age group|avg_balance|
|---------|---------|
|*-30|5187.5|
|30-40|33427.5|
|40-50|11300|
|50-*|34192.5|


 