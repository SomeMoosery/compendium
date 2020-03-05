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

**Client-Server Architecture:** This architecture works on the **request-response model**. A client (usually a tablet, desktop, mobile) sends a request for information to a server, and the server responds with that information.

## Definitions: 

**Stored Procedures:** storing business logic code in a database. 
1. Often requires vendor-specific language (or at least vendor-specific frameworks/extensions to a language)
2. Hard to test since they need to be executed in the context of a database
3. Are tricky to version control
4. Are tricky to treat as a first-class application

**API Gateway:** an HTTP server where routes/endpoints are defined in config, and each route is associated with a resource to handle that route (in Serverless, handlers are often FaaS functions) 

**Monitoring-Driven Development:** Once code has passed basic unit-test validation, deploy to a small subset of traffic and see how it compares to the previous version. This is basically testing in production, you savage.