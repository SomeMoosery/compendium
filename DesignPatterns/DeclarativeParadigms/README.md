# Declarative Paradigms

**Logic, functional, and domain-specific** languages belong within declarative paradigms (i.e. HTML, XML, Haskell, SQL)

Focuses on building logic of software without actually describing its flow (i.e. saying _what_ without adding the _how_)
- Not always Turing-complete (but mostly is)

Functional programming based on lambda calculus is Turing complete, avoids states, side effects, and mutation of data. You create expressions (not statements) and evalutate functions.
- No loops
- For the same argument, a function will always return the same value

## Three Principles of Functional Programming:
1. Functions are standalone values 
- can be treated like any other standalone value like ints or strings
- can be assigned to variables, stored in lists, passed as parameters, returns to results, etc...
2. Composition is the primary way to build system
- two functions can be _composed_ by connecting the output of one to the input of another. The result is another function that can be used as a starting point for more composition
- 