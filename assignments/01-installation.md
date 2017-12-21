# Assignment 1

## Installing Elasticsearch and Kibana

Install Elasticsearch and Kibana on a local machine.

### How to validate an assignment

**1**  
Once you install and start Elasticsearch, go to http://localhost:9200. You should see a JSON like this:

```javascript
{
  "name": "DjmE-46",
  "cluster_name": "elasticsearch",
  "cluster_uuid": "TN5YcNNcTOGWAFBm3M6z_A",
  "version": {
    "number": "6.0.1",
    "build_hash": "601be4a",
    "build_date": "2017-12-04T09:29:09.525Z",
    "build_snapshot": false,
    "lucene_version": "7.0.1",
    "minimum_wire_compatibility_version": "5.6.0",
    "minimum_index_compatibility_version": "5.0.0"
  },
  "tagline": "You Know, for Search"
}
```

**2**  
If you configured Kibana correctly, to point to the Elasticsearch server URL, when you start Kibana
go to http://localhost:5601/app/kibana#/dev_tools/console. You should see Kibana Console 
(part of *Dev Tools*).  

### Detailed Description

Follow the guides on:
* https://www.elastic.co/downloads/elasticsearch 
* https://www.elastic.co/downloads/kibana

In order to run Elasticsearch you should have Java 8+ installed on the system. 
In case you do not have Java installed go to http://www.oracle.com/technetwork/java/javase/downloads/index.html,
download the JDK version and install it.

*Note: Starting Kibana can take some moments.*

