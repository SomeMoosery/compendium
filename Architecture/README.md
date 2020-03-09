# Web Application & Software Architecture 101:

## Tiers of Applications:
1. 1-Tier: All the code is on one machine (client, database, everything) - this is what like video games, MS Excel, etc are
    - No network latency
2. 2-Tier: All the client code and business logic is on one machine, while the database is on another 
    - Good for if you need to minimize network latency
3. 3-Tier: The client is on one machine, the business logic (web server) is on another, and the database on a third
4. N-Tier Application: Distributed Application: a three-tier application with more components such as:
    - Cache
    - Message queues for asynchronous behavior
    - Load balancers
    - Search servers for searching through massive amounts of data
    - Components involved in processing massive amounts of data
    - Components running heterogeneous tech (web services, microservices) 

N-tier applications make use of the **Single Responsibility Principle** of our SOLID principles, meaning that we want each component (tier) of our application to have one job (saving data, running business logic, etc).
- When upgrading your designated database server, whose responsibility is only persisting data, even if something were to go wrong it would only affect this one component (and the features relying on it, but it's atomic).
- This is why **stored procedues** is becoming on outdated principle. What if we want to switch to a different database in the future? We'd have to refactor add this business logic to the new database or refactor application code to fit this business logic in here. It's not atomic.
- Now, different services can use the same database, or messaging server, or any component (tier) since they're not tightly coupled. **This makes scaling much easier**

Do not confuse **tiers** with **layers**. Pretty much any tier application will have a similar layout of layers.
- Layers are typically the presentation layer (UI), Controller (REST API, receiving inputs), Service (processing), DAO (storing and receiving from database), and Database (the DB). 
    - Layers represent the organization of code and breaking it into components
- Tiers involve the physical separation of components
    - There's one tier to deal with the database meaning one (or many) computers running the DB, there's another tier (meaning one or many computers) for running the application code, or messaging queue, etc...

## Intro to Web Architecture
**Web Architecture:** multiple components like a database, message queue, cache, UI, etc running in conjunction with each other to form an online service 

![Basic Web Architecture](images/basic-architecture.png?raw=true "Basic Web Architecture")

### The Client

**Client-Server Architecture:** This architecture works on the **request-response model**. A client (usually a tablet, desktop, mobile) sends a request for information to a server, and the server responds with that information.

The **client** holds the user interface, written in HTML, JavaScript, CSS.
- responsible for the look & feel of the application
- the most common web-based technologies for writing web-based UIs are React, Angular, Vue, jQuery, etc...
    - Android, Windows OS, iOS all have different technologies to write their UIs as well

Types of Clients:
1. **Thin Client:** a client which holds just the user interface of the application, no business logic of any sort (three-tiered applications)
2. **Thick (Fat) Client:** a client which holds all or some part of the business logic (two-tiered applications)
- Thicc client is good for if you want low bandwidth because there's fewer calls to the backend here

![Basic Client](images/basic-client.png?raw=true "Basic Client")

### The Server
The primary purpose of a web server is to receive the requests from the client & provide the response after executing the business logic based on the request parameters received from the client

**application server:** server running a web application

Along with application servers, there are:
- proxy servers
- mail servers
- file servers
- virtual servers

Every component of a web application needs a server to run (database, message queue, cache, UI)

**server-side rendering:** rendering the UI on the backend and sending the rendered data to the client 

### Communication Between Client & Server
In a **request-response** model, the entire communication happens over the **HTTP protocol**. 

**HTTP Protocol:** a request-response protocol for data exchange over the World Wide Web
- It is stateless - every process over HTTP is executed independently and has no knowledge of previous processes

The backend has a REST API which acts as an interface to the outside world of requests. Every request (whether it's from the client written by the business or a third-party developer consuming our data, has to hit REST-endpoints to fetch the data)

### REST APIs
**REST:** a software architectural style for implementing web services
- web services implemented using the REST architectural style are known as the **RESTful Web services**
- enables servers to cache the response that improves the performance of the application (usually, GET requests are cacheable by default)

**REST API:** an API implementation that adheres to the REST architectural constraints, acting as an interface.

Since communication is stateless, you need to send authentication information from client to server on each request.

A **REST endpoint** is just the url of a service!
- `https://myapp.com/getuserdetails/{username} is a REST endpoint for fetching the user details of a particular user from the application
- this URL is exposed to all its clients 
- this completely decouples the client and server. It doesn't matter what the client looks like or what it does, as long as it hits that endpoint, it's good to go

**Back in the day, people would put business logic in JSP (`<% ...java code... %>`) tags, tightly coupling client and server - meaning you'd have to duplicate code when writing a web client, and a mobile client, etc...

The REST API acts as a gateway which encapsulates the business logic. It handles all the client requests, taking care of the authorization, authentication, sanitizing the input data & other necessary tasks before providing access to the application resources

![Basic REST API](images/basic-rest-api.png?raw=true "Basic REST API")

![REST API Gateway](images/rest-api-detail.png?raw=true "REST API Gateway")

HTTP requests consume bandwidth, which costs the business money each time

Clients use **AJAX (Asynchronous JavaScript & XML)** or GET to send requests to the server in the HTTP Pull based mechanism

AJAX enables us to fetch updated data from the server by automatically sending requests over and over at stipulated level, otherwise known as **polling**
- upon receiving the updates, a particular section of the web page is updated dynamically by the callback method. 
    - We see this behaviour all the time on news & sports websites, where the updated event information is dynamically displayed on the page without the need to reload it.
- AJAX uses an XMLHttpRequest object for sending the requests to the server which is built-in the browser and uses JavaScript to update the HTML DOM
- AJAX is commonly used with jQuery
    - There's many different approaches to take with React

![AJAX](images/basic-ajax.png?raw=true "REST API Gateway")

If a client doesn't receive a response from the server within the TTL, the browser kills the connection and the client has to re-send the request

If we're certain that the response may sometimes take more time than the TTL set by the browser, use **persistent connection**

**persistent connection:** a network connection between the client & the server that remains open for further requests & the responses, as opposed to being closed after a single communication. This is an HTTP Push

For HTTP Push, clients use:
- Ajax Long polling
    - the server holds the response until it finds an update to be sent to the client
    - server never returns an empty response
    - fewer requests from client to server than regular polling
- Web Sockets
    - preferred when we need a _persistent bi-directional low latency data_ flow from the client to server & back
    - messaging, chat, real-time social streams 
    - **DOESN'T RUN OVER HTTP, BUT TCP. YOU NEED TO UPGRADE FROM HTTP -> TCP, AND BOTH CLIENT AND SERVER MUST HAVE TCP ENABLED FOR THIS TO WORK**
- HTML5 Event Source
    - the server automatically pushes the data to the client whenever the updates are available (once the client has established initial connection)
    - one-directional as compared to web sockets
    - gets rid of a huge number of blank request-response cycles cutting down the bandwidth consumption
- Streaming over HTTP
    - ideal for cases where we need to stream large data over HTTP by breaking it into smaller chunks

![HTTP Pull Versus HTTP Push](images/pull-vs-push.png?raw=true "HTTP Pull Versus HTTP Push")

These persistent connections will use **heartbeat interceptors** to keep the browser from killing the connection

These are very resource intensive!

## Scalability

**Scalability:** the ability of the application to handle and withstand increased workload without sacrificing latency
- if you have one user, one hundred users, or one billion users, your app should take the same amount of time to response to each of the billion concurrent user requests

**Latency:** the amount of time a system takes to respond to a user request
- our goal if for minimal latency - scalability is really just creating minimum latency no matter how much traffic is load ona  system builds up
- measured as the time difference between the action that the user takes on the website and the system response to that webiste 

Two types of latency:
1. **Network Latency:** the amount of time that the network takes for sending a data packet from point A to point B
- the network should be efficient enough to handle the increased traffic load on the website
- **_this is where CDNs are useful - they deploy code globally to be as physically close to the user as possible_**
2. **Application Latency:** the amount of time the application takes to process a user request
- continuously try stress/load tests in order to pinpoint bottlenecks to fix in order to minimize latency

![Latency](images/latency.png?raw=true "Latency")

### Types of Scalability
1. **Vertical Scaling:** adding more power to your server
- increasing RAM on your machine is vertical scaling
- this is also called _"scaling up"_
- simpler. Less administrative, monitoring, management efforts since there's no distributed configurations to do
- low availability

2. **Horizontal Scaling:** adding more hardware to the existing hardware resource pool
- also called _"scaling out"_
- allows ability to scale infinitely and in real-time
- allows for high availability 
- **needs to be stateless** - no static instances in classses. Static classes hold application data and if a particular server goes down, all the static data/state is lost
    - use persistent memory like a key-value store to hold data

### Typical Scalability Bottlenecks
1. Database
- if your database isn't scaled, you're going to hit bottlenecks even if your servers are scaled beautifully
- you could be using the wrong type of database (more on this in the Databases section below)
    - if you want transactions and strong consistency, use a relational database
        - strong consistency requires you to lock all nodes until the replication is complete
    - if you don't need consistency and would rather have horizontal scalability on the fly pick a NoSQL database

    Solutions to scaling your database: 
    1. Sharding

2. Application architecture
- a common architectural mistake is not using asynchronous processes & modules wherever required, and having all the processes scheduled sequentially
    - for instance, if a user uploads a document, tasks such as sending a confirmation email to the user and sending a notification to subscribers/listeners on the upload event should be done asynchronously
    - these types of tasks should be forwarded to a **messaging server** 

3. Not using caching wisely (or not using it enough)
4. Inefficient configuration and setup of load balancers (using too many or too few)
5. Don't add business logic to the database (stored procedures)
6. Bad code (nested loops, tightly-coupled code, really just not paying attention to Big-O)

### Fine-Tuning Performance
1. Profile everything in the code (application profiling, code profiling)
- **profiling:** the dynamic analysis of our code, measuring the space and the time complexity of our code and displaying issues like concurrency errors, memory errors & robustness & safety of the program
2. Cache everything, wherever you can
3. Use a CDN
4. Compress data - store data in a compressed form (lower latency, faster download speeds of data on the client)
5. Avoid unnecessary client server requests, combine multiple requests into one if need be

### Testing Scalability 

During scalability testing, different system parameters are taken into account such as 
- CPU usage
- network bandwidth consumption
- throughput
- the number of requests processed within a stipulated time
- latency
- memory usage of the program
- end-user experience when the system is under heavy load

In this testing phase, simulated traffic is routed to the system, to study how the system behaves and how the application scales under the heavy load

## High Availability

**High availability:** the ability of the system to stay online despite having failures at the infrastructural level in real-time

Systems can fail for a number of reasons (softare crash, hardware failure, human, error, planned downtime, etc...)

### Some Solutions for HA

1. **Fault Tolerance:** the ability of the system to stay up despite taking hits
- when one (or a few) instances/nodes go down, the system will slow down a bit, but not crash
    - for example, if image upload for a social app fails, while that may not work, the entire system still will
    - this is known as **fail soft**
    - microservices help to alleviate this

2. **Redundancy:** duplicating the components or instances & keeping them on standby to take over in case the active instances go down
- a fail-safe mechanism
- a common way to do this is by having some active nodes, which replicate to passive (unused) nodes which only spin up and run if one of the active nodes goes down 
    - can also have active-active replication where you just have all the nodes always running so that if one goes down, it's not a big deal
- removes many single points of failure
- geographically distribute the workload so that even in the case of a natural disaster, software stays up and running

Can use **high-availability clustering** in order to ensure HA
- **Highly-available cluster:** nodes in a cluster connection by a private network (**heartbeat network**) that continuously monitors the health/status of each node in the cluster
    - a single state across all nodes in the cluster is achieved with a **shared distributed memory** and **distributed coordination service**
    - use techniques like **disk mirroring**, **RAID**, redundant network connections, redundant electrical power, etc
    - run together in the same geographical zone

![Redundancy](images/redundancy.png?raw=true "Redundancy")

## Monolith vs Microservice

**Monolithic application:** a self-container, single-tier application that has its entire application code in a single codebase
- typically has a UI, business layer, data access layer, and the database
- simpler than microservice architevture to build, test, deploy, monitor, and manage since all the code is in one place
- continuous deployment is an issue as one change anywhere in any layers means re-deploying the whole application
- thorough regression testing required since layers are tightly coupled
- many single points of failure (if one part of one layer breaks, the whole application could go down)
- flexibility and scalability become issues as the application grows 
- can really only use one tech stack on them
- typically holds state, so not necessarily cloud-ready

**Microservice application:** an application in which different features/tasks are split into separate respective modules/codebases which work in conjunction with each other forming large services as a whole
- Single Responsibility and Separation of Concerns principles are satisfied here
- easier cleanup and maintenance, feature development, testing and deployment 
- inherently designed to scale
- each part of the application is seen as a product, not a project, with its own codebase, database, etc
- no inherent single points of failure
- can leverage heterogeneous technology
    - every service interacts with each other via an API Gateway, so as long as each tech can interpret REST, you can have one service in Go, another in Ruby, etc...
    - can even use **polyglot persistence:** using multile database types (SQL and NoSQL) together
- managing services becomes more difficult
    - need a node manager like Apache Zookeeper, a distributed tracing service for monitoring the nodes, etc
- eventual consistency across nodes

![Monolith vs Microservice](images/monolith-vs-microservice.png?raw=true "Monolith vs Microservice")

## Databases

### Types of Data:
1. Structured Data
- confirms to a certain structure, typically stored in a database in a normalized fashion
- no data preparation logic necessary - direct interaction can be done with structured data
- generally managed by a query language like SQL

2. Unstructured Data
- no definite structure
- heterogeneous data - text, image files, video, PDFs, Blobs, Word docs, etc...
- often encountered in data analytics
- raw data is taken in and needs to flow through a data preparation stage which separates it based on some business logic and then runs analytics algorithms on it

3. Semi-structured Data
- stored in data transport formats (XML, JSON)

4. User State
- where the user clicks, items added to a cart, etc...
- improves browsing experience for the user

### Types of Databases:
1. Relational Database
- saves data containing relationships (one-to-one, one-to-many, many-to-many, etc)
    - for example, one row in table T1 can correspond to many rows in table T2 (one-to-many), for exmaple of T1 had an ID column, and T2 had a column T1ID that corresponded to T1's ID
- ensures **data consistency:** saving data in a normalized fashion
    - a unique entity occurs in only one place/table, in its simplest and atomic form and is not spread throughout the database
    - helps with updating - we only need to fetch that one entry to update it
- ensures **ACID Transactions**
- need to be **sharded** or **replicated** to make them run smoothly in a cluster - this makes it **difficult to scale**
- **Use Relational Databases When:**
    1. You value transactions and data consistency (anything to do with numbers, a social network, etc) since it adheres to ACID properties
    2. Your data is largely relationship-based (you want to group by or compare certain columns regularly)

2. NoSQL Database
- think of this more as a JSON-based database!
- built for high frequency reads/writes, just fetch data with its **key** (or the ID), which is O(1)
    - since we don't have strong consistenty (which requires locking all nodes until the transaction is complete), we can read/write much more quickly than relational. Were it not for this, relational would be just as quick!
- much easier to scale - often comes with built-in horizontal scaling capabilities
    - designed to run intelligently on clusters (minimal human intervention needed)
- do not have strong consistency (it has eventual consistency) or ACID transactions - necessary to sacrifice this to scale more easily
    - for example, sometimes YouTube could show that a video has 10 views with 15 likes. This just means that when you requested this video, your computer received the up-to-date like count, but a slightly oudated view count
- requires less thought on initial setup than relational database
- schemaless
- since an entity is spread throughout the database, it needs to be updated in all places (not just the one place like for relational databases, where data is normalized)
- has more specific types of databases (time-series, wide-column, document-oriented, etc) for data analytics

### Specifically, Types of NoSQL Databases
1. **Document-Oriented Databases:** store data in a document-oriented model in independent docuemnts
- semi-structured data, stored in a JSON-like format
- flexible, can change easily over time 
- horizontal scalability, performant read-writes, caters to CRUD use cases
- MongoDB, CouchDB, Amazon DocumentDB

2. **Graph Databases:** store data in nodes/vertices and edges in the form of relationships
- each node is an _entity_
- each edge is a _relationship between nodes_
- lower latency than relational database
    - relationships aren't calculated at query time in the form of joins, they're just stored as edges and you just have to fetch them
- good for social graph, recommendation apps, fraud detection, etc...
- Neo4j

3. **Key-Value Store:** use a simple key-value method to store and quickly fetch data with minimum latency
- good for caching in applications (ensures minimum latency)
- the key is a unique identifier and the value can be an object or something complex even like a graph
- **along with caching, this is used for persisting user state or user sessions, managing real-time data, implementing queues, or implementing pub-sub systems**
- Redis, Memcached

4. **Time Series Database:** optimized for tracking & persisting time-series data
- good for managing data in real-time continually over a long period of time 
- used in IoT, industry sensors, self-driving vehicles, stock market financial data, etc
- lets you study user patterns, analytics
- InfluxDB, Prometheus

5. **Wide Column Database:** optimized for storing tables with upwards of billions of columns
- good for big data
- Cassandra, Scylla DB

### ACID Transactions - Atomicity, Consistency, Integrity, Durability.
- if a transaction in a system occurs, either it will be executed with perfection without affecting any other processes or transactions, or it won't be executed at all (it will roll back)
    - the system will have a new state which is durable and consistent 
    - only has two states (old state, and new state) - no intermediary steps

**CAP Theorem:** An application can either be Consistent and Partition Tolerant, or Available and Partition Tolerant, but not both! 

### Polyglot Persistence Use Case: Social Media
- to store the relationships amongst people (users and friends, food preference, etc) - relational database (MySQL)
- for low-latency access to frequently-accessed data - key-value store (Redis, Memcached)
    - this can also store user sessions
- wide-column database to run analytics on data generated by users (Cassandra, HBase)
- payments system - relational database (MySQL)
- recommendation system - Graph database
- scalable search feature that persists serach-related ata - document-oriented store (ElasticSearch)

Can also use a **Multi-Model Database** (like Couchbase) to manage multiple persistence technologies in a single service

![Polyglot Persistence](images/polyglot.png?raw=true "Polygloat Persistnce")

![Multi-Model Database](images/multi-model.png?raw=true "Multi-Model Database")

## Definitions: 

**Stored Procedures:** storing business logic code in a database. 
1. Often requires vendor-specific language (or at least vendor-specific frameworks/extensions to a language)
2. Hard to test since they need to be executed in the context of a database
3. Are tricky to version control
4. Are tricky to treat as a first-class application

**Business logic:** the part of a computer program that contains the information (in the form of business rules) that defines or constrains how a business operates.

**API Gateway:** an HTTP server where routes/endpoints are defined in config, and each route is associated with a resource to handle that route (in Serverless, handlers are often FaaS functions) 

**Monitoring-Driven Development:** Once code has passed basic unit-test validation, deploy to a small subset of traffic and see how it compares to the previous version. This is basically testing in production, you savage.

**Thin Client:** a client which holds just the user interface of the application, no business logic of any sort (three-tiered applications)

**Thick (Fat) Client:** a client which holds all or some part of the business logic (two-tiered applications)

**application server:** server running a web application

**HTTP Protocol:** the protocol for data exchange over the World Wide Web

**server-side rendering:** rendering the UI on the backend and sending the rendered data to the client 

**REST:** a software architectural style for implementing web services

**REST API:** an API implementation that adheres to the REST architectural constraints, acting as an interface.

**REST endpoint:** the url of a service

**HTTP Pull:** when the client sends a request to a server, it's pullingdata

**HTTP Push:** the client sends the request for particular information to the server, just for the first time, & after that the server keeps pushing the new updates to the client whenever they are available
- **also known as a callback**

**Polling:** automatically sending requests over and over at stipulated level

**heartbeat interceptors:** blank request responses between the client and the server to prevent the browser from killing the connection

**Scalability:** the ability of the application to handle and withstand increased workload without sacrificing latency

**Latency:** the amount of time a system takes to respond to a user request

**Vertical Scaling:** adding more power to your server

**Horizontal Scaling:** adding more hardware to the existing hardware resource pool

**Cloud Elasticity:** the ability for cloud services to be able to "stretch" up/down, out/in cheaply, with ease

**Profiling:** the dynamic analysis of our code, measuring the space and the time complexity of our code and displaying issues like concurrency errors, memory errors & robustness & safety of the program

**High availability:** the ability of the system to stay online despite having failures at the infrastructural level in real-time

**Fault Tolerance:** the ability of the system to stay up despite taking hits

**Fail soft:** when part of a system can fail, but the remaining parts remain operational

**Redundancy:** duplicating the components or instances & keeping them on standby to take over in case the active instances go down

**Polyglot persistence:** using several different persistence technologies (realtional database, NoSQL) to fulfil different persistence requirements in an application

**Data consistency:** saving data in a normalized fashion

**Multi-Model Database:** databases that support multiple data models like Graph, Document-Oriented, Relational etc. as opposed to supporting only one data model

**Document-Oriented Databases:** database that stores data in a document-oriented model in independent docuemnts

**Graph Databases:** database that stores data in nodes/vertices and edges in the form of relationships

**Key-Value Store:** database that uses a simple key-value method to store and quickly fetch data with minimum latency

**Time Series Database:** database optimized for tracking & persisting time-series data

**Wide Column Database:** database optimized for storing tables with upwards of billions of columns

## Resources:
[Introducing WebSockets](https://www.html5rocks.com/en/tutorials/websockets/basics/)
[Educatvie Course](https://www.educative.io/courses/web-application-software-architecture-101)
[Push vs Pull - Jeff Poole](https://medium.com/@_JeffPoole/thoughts-on-push-vs-pull-architectures-666f1eab20c2)
[How production engineers support global events on Facebook](https://engineering.fb.com/production-engineering/how-production-engineers-support-global-events-on-facebook/)
[The 12-Factor App](https://12factor.net/)
