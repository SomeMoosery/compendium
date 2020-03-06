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

## Resources:
[Introducing WebSockets](https://www.html5rocks.com/en/tutorials/websockets/basics/)
[Educatvie Course](https://www.educative.io/courses/web-application-software-architecture-101)
[Push vs Pull - Jeff Poole](https://medium.com/@_JeffPoole/thoughts-on-push-vs-pull-architectures-666f1eab20c2)
[How production engineers support global events on Facebook](https://engineering.fb.com/production-engineering/how-production-engineers-support-global-events-on-facebook/)
[The 12-Factor App](https://12factor.net/)
