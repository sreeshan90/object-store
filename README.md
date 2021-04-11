# Concurrent Read optimized Data Store

### Assumptions

* No persistent storage. Only storage until JVM restarts
* Object here is a User with three fields - uuid, name, email

### Implementation Specifics

This data store uses in-memory concurrent B-Linked-Tree to store the user data. This data structure was chose
because the insert, delete and search operations support logarithmic complexities and suitable for the concurrency needs
of the application.

The store uses a caching layer with least recently used cache eviction policy.
The cache is implemented using a linked hash map and overriding the removeEldestEntry method.
A hashmap was chosen for the cache because of the constant time lookup. This facilitates a faster lookup of objects.

To handle the large bursts of write, a queuing mechanism is in place. The insert call always inserts the 
incoming request on to a queue. From the queue, a consumer service processes the users and inserts them into
the store in an async fashion.

### Execution steps:

1. git clone this repo
2. docker build -t theom/object-store .
3. docker run -p 8080:8080 theom/object-store


Postman Collection available inside the repo - under /src/main/resources


