## Go-Kit

Go-kit is a toolkit for microservices, similar to Spring Boot and the like.

Three major components in a go-kit-based architecture:
1. **Transport Layer**
2. **Endpoint Layer**
3. **Service Layer**

You go through transport -> endpoint -> service

**_See the `GoMicroServices` example for reference_**

### Trnasport Layer
When you're building microservices-based distributed systems, services typically communicate with each other via concrete transports like HTTP or gRPC, or with a pub-sub system like NATS.

The transport layer in go-kit is bound to concrete transports
- go-kit supports various transports for servicing services using HTTP, gRPC, NATS, AMQP, Thrift

Since Go services are only focused on implementing business logic **and have no knowledge about concrete transports**, you can have multiple transports for the same service (you can expose a single go-kit service using HTTP and gRPC)

In the go-kit repo, you can see that in [the `transport` directory](https://github.com/go-kit/kit/tree/master/transport) there are subdirectories for NATS, AMQP, etc... 
- the transports are basically the highest layer of the service and deal with whatever kind of communication between services your architecture uses (whether that's RPC over HTTP, NATS, AMQP (RabbitMQ), etc...)

**Sets the actual endpoints (kind of confusing) like `/uppercase` and `/count` and creates endpoints**

### Endpoint Layer
The fundamental building block of servers and clients. 
- represents a single RPC (go-kit's default messaging pattern, but doesn't have to be) method
- each service method in a go-kit service converts to an endpoint to make RPC-style communication between servers and clients 

Each endpoint exposes the service method to outside world using Transport layer by using concrete transports like HTTP or gRPC
- a single endpoint can be exposed by using multiple transports

**Handles the requests and responses (takes in a request, calls the service logic, returns a response)**

### Service Layer
The actual implementation of the business logic

Go-kit services are modeled as interfaces

The business logic in the services contain core business logic, which should not have any knowledge of endpoint or concrete transports like HTTP or gRPC, or encoding and decoding of request and response message types

Each service method converts as an endpoint by using an adapter and exposed by using concrete transports
- because of the clean architecture, a single Go kit service can be exposed by using multiple transports.

**This would be the interface and implementation of the methods on a String (uppercase, count)**
