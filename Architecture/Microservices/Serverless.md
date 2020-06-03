# Serverless Architecture

**_Serverless architectures may benefit from significantly reduced operational cost, complexity, and engineering lead time, at a cost of increased reliance on vendor dependencies and comparatively immature supporting services._**

When you run a serverless architecture, typically:
- The client (frontend) becomes richer. It may have to hold user session, understand UX structure of app, read from DB & translate it in to a usable view, etc 
- Things like auth from are taken out from the server and into a BaaS product like Auth0, and the database from our hands into a BaaS product like Firebase
- **Choreography over orchestration** - each component plays a more architecturally-aware role (common in microservices-approach anyway, think smart endpoints dumb pipes) 
- Asynchronous message processing (user clicks a button, the server sends necessary data through a messaging channel to a click processor function implemented in a FaaS environment (i.e. a new Lambda function is triggered each time) 

## FaaS Core Principles: 
1. Run backend code without managing your own server systems or long-lived server applications (difference between Serverless and containers or PaaS) 
- Faas replaces the, for example, click-processing server (possibly on a physical machine, but definitely a specific application) with something that doesn’t need a provisioned server nor an application constantly running
2. Doesn’t require specific framework or library. Very easy to transition this code from you old server to FaaS - basically, just delete the main method (the setup) 
3. Deployment is very different (FaaS provider does everything necessary for provisioning resources, instantiating VMs, managing processes, etc)
4. Horizontal scaling is automatic and elastic
5. Functions triggered by provided-defined event types (S3 file/object update, time, messages added to message bus (Kinesis), OR as a response to inbound HTTP requests (enabled through API Gateway) 

FaaS is “stateless” - or really, any state that’s required to be persistent must be externalized outside of the FaaS function instance 
    - **Stateless:** function/code/app/whatever that provides a purely functional transformation from their input to their output
    - State-oriented functions will make use of a DB, cross-application cache (Redis), or network file/object store (S3) to store state across requests 

Functions can only run a max of ~5 mins before timing out

Cold starts (spinning up everything from scratch is costly, and only happens initially or when the function isn’t called in a while as AWS retires unused functions after a couple of minutes) 

![API Gateway Example](images/basic-api-gateway.png?raw=true "API Gateway Example")
**API Gateway:** an HTTP server where routes/endpoints are defined in config, and each route is associated with a resource to handle that route (in Serverless, handlers are often FaaS functions) 
- AWS’ API Gateway is a BaaS service since it’s an external service you configure, but don’t need to run/provision yourself 

PaaS applications are not geared towards bringing entire applications up and down in response to an event, whereas FaaS does exactly that
- it may be that FaaS is seen as a better choice for an event-driven style with few event types per application component, and containers are seen as a better choice for synchronous-request–driven components with many entry points.
- You’re outsourcing sysadmin 

## Benefits of Serverless:
1. Reduced operational cost (economies of scale)
- Sharing infrastructure (hardware, networking) with other people
- Labor cost gains (you spend less time on gruntwork and outsource a lot of this to serverless service providers)
2. Reduced development cost (benefit of Baas) - since you’re just outsourcing work you’d normally have to do yourself, you can use things like Auth0 and Firebase to offload the work of auth/databases to managed services
- You pay someone else to manage servers, databases, and even application logic that you might otherwise write yourself
- IaaS and PaaS are based on the premise that server and OS management can be commodified. BaaS is a result of **entire application components** being commodified
3. You only pay for the compute you need (benefit of FaaS)
- **Occasional requests:** If you're running a server app that processes one request per minute, and it takes 50ms to process, deploying this on a dedicated server would be wildly inefficient. With serverless, you pay for just 100ms (the smallest incrememnt of compute for AWS) per minute.
    - Can break down microservices by logic/domain even if the operational costs may be prohibitive
    - Can try out a new service for minimal cost (or much less than deploying on dedicated machines)
- **Inconsistent traffic:** If you typically have 10 requests per minute, but every hour get 1000 requests per minute, running a serverless architecture with FaaS components renders this a nonissue because it will automatically scale horizontally to fit this need
    - _On the flipside_ if your traffic is super consistent and you're utilizing your servers efficiently all the time then FaaS may actually be more expensive
4. Optimizing code directly lowers operational costs. If you code running as a FaaS which took 500ms, you're paying 5 100ms blocks. If you can optimize your code to run in 200ms, you just shaved 60% of your operational costs.
5. Easier operational management
- You don't have to worry about manually scaling up or down your array of servers
- Reduced packaging and deployment complexity - it's as simple as uploading the .zip of your FaaS to AWS (or similar)
    - PaaS solutions have similar deployment benefits, but without the scaling advantages of FaaS
- Very quick time to market and easier experimentation
6. Greener computing since we aren't keeping dedicated servers consistently running

## Persistent Drawbacks of Serverless
1. Vendor control - you give up some of your system to a third party
- You're helpless against their system downtime, limits, cost changes, loss of functionality, forced API upgrades, etc
- You'll have strong constraints on how to use the vendor so that the vendor can deliver on its reliability goals to _all_ of its customers.
2. Multitenancy - this isn't really an issue anymore with AWS, but while they want you to think and they make it seem that you're running your code on a dedicated server (with VPC), you're really not
- there could always be security problems (you can see others' code or vice versa), robustness problems (a bug in someone's code causes your code to crash), and performance (high load on someone's code causes yours to slow down)
3. Vendor lock-in - once you sink your teeth into one cloud provider, it becomes difficult to port over to another
4. Security concerns
    - Each serverless vendor you work with adds an additional security implementation to your system, increasing your surface area for malicious intent
    - If using BaaS directly from mobile platform, you lose the protective barrier of a server-side app
    - FaaS functions are usually paired with IAM policy, and IAM management is complex and easy to mess up
5. Repetition of logic across client platforms - with traditional architectures, you can host (for example) code to communicate with your database on the server side. With serverless, where most if not all of your code is on the client side (and the rest is in BaaS if you're running a full BaaS architecture), you might have to rewrite the logic for each client (iOS, Android, web, etc) to communicate with the database from the client side (**_Only for full BaaS_**)
- Additionally, using this example, if you want to switch databases, you'll have to replicate that change across each client
6. Loss of server optimizations - you simply can't optimize you server design, since it's not your servers (you can still optimize your FaaS functions though)
**7. NO IN-SERVER STATE FOR SERVERLESS FAAS** - since you have no control over when a host container starts/stops, you can't assume the state from one invocation of a function will be available to another invocation of the same function
- Follow Step 6 of the 12-Factor App. **Twelve-factor processes are stateless and share-nothing. Any data that needs to persist must be stored in a stateful backing service, typically a database.**
- With PaaS like Heroku, you can bend the rules because you have control of when Heroku Dynos are started and stopped. You can't bend these rules with Faas
- **If you can't keep state in memory, you should use a fast NoSQL database, out-of-process cache (Redis, Memcached), or external object/file store (S3) - much slower than an in-process cache**
    - FaaS allows for some local cache. Lambdas stick around for a few hours as long as they're invoked every couple of minutes, where we can take advantage of 3GB RAM and 512MB local "/tmp" space

## Drawbacks than can (or have) been eliminated
~~1. Configuration - Lambda is super configurable now~~
2. You can DoS yourself - you only can run so many Lambdas at one time per AWS account. If you're running a performance test and spin up 1000 Lambda instances, you're going to crash your entire system and getting exceptions or queueing. Even if you use multiple AWS accounts, something like batch processing could bring down a server
- AWS provides **reserved concurrency** which limits the concurrency of a Lambda so it doesn't blow up the rest of your account, while simultaneously making sure there is always capacity available no matter what the other functions are doing. This isn't super stable though 
3. Execution duration - Lambdas will abort if they run longer than 5 minutes
4. Startup (specifically cold starting) takes a long time 
5. Testing
- Unit testing is easy, since each function is just code without many custom libraries, interfaces, etc
- Integration testing is hard
    - For BaaS, you're relying on externally provided systems. Should your tests use these external systems? How amenable are thsoe systems to testing scenarios? Can you easily tear up/down state? Can your vendor give a different billing strategy for load testing? Does the vendor provide a local stub simulation? 
    - For FaaS, you are able to run this code locally, but it can't really simluate the cloud environment (so local FaaS functions should really just be used for debugging, etc). You should also isolate your FaaS tests from your production cloud accounts to make sure you don't DoS yourself
~~6. Debugging FaaS is mostly fixed~~
~~7. Deployment, packaging, and versioning are rapidly imporving~~
~~8. Discovery - making sure that one service always calls the correct version of another service. Putting these services behind an API Gateway more or less eliminates this issue~~
9. Monitoring and Observability - there is some basic monitoring for Lambda, but this is entirely vendor-dependent and varies depending on the vendor you choose
10. Overly-ambitious API Gateways - since you're paying per request through the API Gateway, wouldn't it make sense to perform some application-specific logic within the gateway itself since API Gateways are often the link between frontend-FaaS? Since it is a BaaS, it would make sense to offload some of this work into the gateway, but this also becomes more difficult to test and monitor now.
11. Deferring of Operations - **serverless isn't no-ops**. There's still a lot to do in terms of monitoring, architectural scaling, secruity, and networking. If your site gets 10x more traffic all at once, you may still get DoS-ed. Don't get a false sense of security here.

## The Future of Serverless - what it may acccomplish
The "First 10 minutes" of serverless apps is still a little arduous - this could use improving (and there is improvements being made with [Amazon's Serverless Application Model](https://serverless.com/))

Tooling:
1. Higher-level releases - since we can't atomically release 100 Lambda components at once, we could implement a "mixing desk" where we gradually bring groups of components in and out of the traffic flow. Make blue-green deployment easier, etc...
2. Distributed monitoring is necessary and severely lacking right now
3. Remote debugging would be an amazing feature to have
4. Meta operations would be a welcome addition - how to more effectively look after hundreds of thousands of FaaS, configured services, etc... (grouping, seeing when FaaS services are no longer used)

State Management: right now, the lack of persistent in-server state for FaaS is oftentimes a deal-breaker
- vendors could keep functions around longer to make use of typical in-process caching approaches
- low-latency access to out-of-process data (like being able to query Redis with super low network overhead) would also work

**Likely, we'll end up seeing hybrid serverless and non-serverless applications, where low-latency parts you'll have a regular, long-running server handling initial requests, gathering all context necessary to process that request from its local and external state, then handing off a fully contextualized request to a farm of FaaS functions that don't need to look up the data externally**

Platform improvements should be made by allowing people to pay more - i.e. pay more to have two instances of a FaaS function always available at low latency 

## Questions regarding emerging Serverless patterns: 
- how big can FaaS functions get before they get unwieldy?
- assuming we can atomically deploy a group of FaaS functions, what are good ways of creating such groupings?
- do these groupings map closely to how we’d currently clump logic into microservices, or does the difference in architecture push us in a different direction?
- what are good ways of creating hybrid architectures between FaaS and traditional “always on” persistent server components?
- what are good ways of introducing BaaS into an existing ecosystem?
- what are the warning signs that a fully or mostly BaaS system needs to start embracing or using more custom server-side code?
- how do we logically aggregate logging for a hybrid architecture of FaaS, BaaS, and traditional servers?
- how do we most effectively debug FaaS functions?

**Ultimately, Serverless is being seen as an all-purpose glue for applications (like if you upload a video to an S3 bucket, a Lambda function runs and processes taht data)**

Serverless is making applications regionless, as you can deploy Lambdas in Amazon CDN - and you can run it on all types of devices.

Right now, FaaS largely just moves stuff you can do on one computer to the cloud. In future years, primitives off of FaaS functions will certainly develope

One day, we'd hope to see abstractions over vendor implementations, where you can work seamlessly with AWS and GCP, or at the very least cross over from one to the other with relative ease

Private/internal PaaS will become very popular - a FaaS-style architecture for projects, but submitting to compliance, legal, etc reasons to run the application on-prem.

## Resources
[Martin Fowler's Blog (where I got most of this)](https://martinfowler.com/articles/serverless.html)