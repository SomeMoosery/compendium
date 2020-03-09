# What makes good code

**Coding is not about communication with a computer. Coding is about _communicating_ with humans and _instructing_ computers. Eventually, code gets compiled and translated to zeroes and ones.**

- code has to make sense for other developers that have work with it in the future. A new team that has never seen the code before should be able to open the code and start working on new features or bug fixes.
- **Good software:** software that does what it’s supposed to do and can scale

At a high level, good code is **maintainable**. If it can't be understood, maintained, and extended by other developers than the code is more or less useless.

Stemming off this, maintainable code is **readable** code. If your code is readable, you'll see immeidate improvements in:
1. Comprehensibility
2. Reviewability
3. Error rate
4. Debugging
5. Modifiability
6. Development time
7. External quality

## A list of positive traits good code should have:

### Simplicity
- be concise, but not to the point of obfuscation (don't write in 10 lines what you could write in 5)
- simplify the organization, implementation, and design of your code
- results in more reliable, bug-free code as there's less that can go wrong

### Readability
- write comments
- name variables appropriately
- establish and follow conventions

### Modularity
- good softawre erects large systems from smaller ones, which are built from even smaller building blocks
- components should be as reusable as possible

### Layering
- like your application sits on a framework sits on an OS sits on hardware, your application itself should have layers, too
- higher layers call ones below, which raise events back up **(CALLS GO DOWN, EVENTS GO UP)**
    - lower layers should never know what the higher layers are doing
    - **the essence of an event/callback is to provide blind upward notification**
- modules and layers are defined by APIs, which deliniate their boundaries

### Design
- take time to plan your application before you build it
- spend ~50% of your time on design
- create a functional spec, internal blueprint, and codify APIs in writing

### Efficiency
- your application should be fast and economical, it doesn't hog files, data connections, etc
- plan for performance

### Elegance
- take pride in your code

Ultimately, this all leads into overall **clairty** - finding a **balance** between complexity and simplicity by constantly evaluating different tradeoffs. Bad programs are less often a failure of coding skill than of having a clear goal. 

## Resources: 
[Code Complete - Steve McConnell](https://www.amazon.com/Code-Complete-Practical-Handbook-Construction/dp/0735619670)

[MDSN article on good code - Paul DiLascia](https://docs.microsoft.com/en-us/archive/msdn-magazine/2004/july/%7B-end-bracket-%7D-what-makes-good-code-good)
