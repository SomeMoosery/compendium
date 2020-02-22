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
