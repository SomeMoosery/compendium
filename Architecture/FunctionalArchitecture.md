# Functional Architecture

**IDEA:** The idea of a system being "functional" doesn't only apply to low-level programming, but to system architecture as a wholel React/Elm style frameworks do this, as well as serverless backends.

## Three Principles of Functional Programming:
1. Functions are standalone values 
- can be treated like any other standalone value like ints or strings
- can be assigned to variables, stored in lists, passed as parameters, returns to results, etc...
2. 

## How They Relate to Functional Architecture
1. In functional architecture, the basic unit is also a function, but a much larger business-oriented function i.e. ***workflow***
- each workflow represents a unit of functionality - a feature, use case, scenario, story 
- just as functions are _things_ at the coding level, workflows are _things_ at the architectural level: **basic building blocks**
2. Composition is the primary way to build system
- two functions can be _composed_ by connecting the output of one to the input of another. The result is another function that can be used as a starting point for more composition
![Functional vs Object-Oriented Workflow](./images/function-vs-oo-workflow.png?raw=true "Functional vs Object-Oriented Workflow")
- functional system tend to look like pipelines with inputs and outputs, rather than message-oriented request/response model
- combine specific components needed for a particular business workflow
- new features are defined independently, rather than grouped into a "databases layer" or a "services layer"

**There is no need for the typical layered architecture. There is not "database layer" or "server layer," each function is a self-contained unit that independently serves its own business function.**

The functional model is very similar to approaches like **onion architecture** and **hexagonal architecture**. The **core domain (pure business logic)** is isolated from the infrastructure
- infrastructure code knows about the core domain, but not the other way around
- dependencies are one-way only and I/O is kept at the edges

Using pure code for the business logic means there's a clear distinction between unit testing and integration testing
- unit testing is for core domain, while integration testing is done on the workflow from end to end

**Isolation of business domain from infrastructure is done automatically**
- onion architecture is done by default and does not have to be remembered to be put into practice

**A bounded context can and should make decisions without having to wait on decisions or information from other bounded contexts**
- each bounded context represents the logical encapsulation of a particular business capability
- ensuring that _bounded_ contexts **remain bounded** is crucial
- bounded contexts should contain autonomous workflows, able to do their jobs without depending on other systems

---

## The entity-service antipattern
- services are built around entities instead of workflows
- a single business workflow will require all of these services to collaborate. If any one of them fails, the workflow fails 

### Example: Orders**
**WRONG WAY:** You have arbitrary "order" and "product" services. The "order" service lumps together a disparate collection of functions related to orders

Just because a business workflow involves an entity such as an "order," doesn't mean it has anything in common with other workflows that use that entity

**SCENARIO:** You have a "pay for an order" workflow and a "delete an order" workflow. These both involve orders, but have completely different business logic

---

Business **events** like a customer clicking a button ("button clicked"), an email alert arriving ("email received"), etc trigger workflows 

The output of a workflow is also an event: a notification that tells any downstream workflows that something important has changed in the world

**Changes that are specific to a particular workflow and aren't shared, such as database updates, aren't emitted from the workflow as events.

**This is the key to assembling larger architectures**
- each workflow is triggered by an event, and that workflow in turn generates more events for downstream processes to consume
- if workflows need to be deployed separately and independently, then an external queue, service bus, or Kafka-style event log is the preferred choice

**workflows interact asynchronously. This allows them to stay independent and decoupled in both time and space.**

---

Workflows can be deployed in a number of ways - microservices, independent serverless functions, components inside a modular monolith, etc...

Model-View-Update is a good example of this pattern

![Functional Architecture](./images/functional-architecture.png?raw=true "Functional Architecture")
![Layered Architecture](./images/layered-architecture.png?raw=true "Layered Architecture")


### Sources
[Stripe Increment](https://increment.com/software-architecture/primer-on-functional-architecture/https://increment.com/software-architecture/primer-on-functional-architecture/)