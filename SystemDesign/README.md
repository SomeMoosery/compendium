# System Design Notes/Tips/Tricks/Guides

## Good clarifying questions:
1. Who's going to use it? 
2. How many users are there, and how many do we hope/expect to have longterm? (**Although keep in mind YAGNI**)
3. How are they going to use it?
4. What should the inputs/outputs of the system be?
5. How much data do we expect to handle?
6. How many requests per second?
7. What is the expected read/write ratio?

## Reverse Proxy
A **reverse proxy** is mainly used by server admins to achieve load balancing and high availability. A website may have several web servers behind a reverse proxy server. The reverse proxy server takes requests from the internet and forwards these requests to one of the web servers.
- while nginx can funciton as both a web server and a reverse proxy, many Node.js sites put their Node.js servers behind an nginx server - so the nginx server is the reverse proxy in this case
- usually add a WAF (such as Barracuda) inline with your reverse proxy for added security

![Reverse Proxy Example](images/nginx-reverse-proxy.png?raw=true "Revesre Proxy Example")

- they're often, but not always, the same thing as a load balancer (such as ELB, which is _specifically_ a load balancer). A reverse proxy can do 
    1. **Load Balancing**
    2. **Caching** - it can cache content from the web servers behind it and thereby reduce the load on web servers and return static content back to the client without having to get data from the web server
    3. **Seucrity** - can be done simply by just obfuscating web servers or can add more complex stuff (like a WAF)
    4. **SSL Acceleration** - when SSL is used, the reverse proxy can be the termination point for SSL sessions so that the workload of dealing with encryption is offloaded from the web servers

## CAP Theorem
You can only ever 2 of the 3 following features in a distributed system:
1. **Consistency**: every read receives the most recent write or an error 
2. **Availability**: every request receives a response, without guarantee that it contains the most recent version of the information
3. **Partition Tolerance**: the system continues to operate despite arbitrary partitioning due to network failures

* CP is a good choice is your system requires atomic reads and writes
* AP is a good choice if your system allows for eventual consistency, or when the system needs to keep working despite external errors

## Solid Principles
1. **_S_****ingle Responsibility Principle:** a class should have one and only one reason to change, meaning that a class should only have one job  
- You should have separate `Circle.java` and `Square.java` classes
2. **_O_****pen-closed Principle:** objects or entities should be open for extension, but closed for modification
- Assume you want to add a way to calculate shapes' areas. Instead of having an `AreaCalculator.java` class that requires multiple `if/else` statements to calculate the areas of different shapes, meaning you'd be constantly modifying the `AreaCalculator.java` class. Instead, add an `area()` method to both `Circle.java` and `Square.java`, thus extending two classes.
- Or, you could still have an `AreaCalculator.java` class, but instead of implement a long `if/else`-heavy area method, you just call `shape.area()` from within the class.
- You'd want to create a `Shape.java` interface in this case to ensure that all shapes you implement have this `area()` function now
3. **_L_****iskov Substitution Principle:** Let q(x) be a property provable about objects of x of type T. Then q(y) should be provable for objects y of type S where S is a subtype of T. Every sublcass/derived class should be substitutable for their base/parent class
- If you have a `VolumeCalculator extends AreaCalculator` class, you should be able to use the two interchangeably
4. **_I_****nterface Segregation Principle:** A client should never be forced to implement an interface that it doesn't use or clients shouldn't be forced to depend on methods they do not use.
- You wouldn't want one `ShapeInterface.java` for both 2D and 3D shapes, because you'd never call `volume()` on a 2D shape. Instead, you'd want two separate interfaces `2DShapeInterface.java` and `3DShapeInterface.java` with maybe a "manager" `ManagerShapeInterface` to show they're related.
5. **_D_****ependency Inversion Principle:** Entities must depend on abstractions not on concretions. It states that the high level module must not depend on the low level module, but they should depend on abstractions.
- If you have a class `Shape.java` that in its constructor requires a `MySQLConnection` class, this violates this principle. You're depending on MySQLConnection (the low level module) in order to instantiate the high level module. If you ever decide to switch to NoSQL, you'll have to modify `Shape.java` thus violating the Open-Close principle. Instead, create a `DBConnectionInterface.java` interface and have `MySQLConnection.java` extend it, so therefore in `Shape.java` you can have the `DBConnectionInterface.java`, which even if you switch to NoSQL you won't have to modify this. 