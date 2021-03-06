# Strategy Pattern

BIG IDEA: Using composition over inheritance
- Inheritance is not intended for code re-use
- The algorithm varies independently from the clients that use it

Defines a family of algorithms, and each are encapsulated and interchangeable. Essentially decouples the algorithm from the person using the algorithm.
- If you have a sorting algorithm built in to a piece of code (client), you would have to change the sorting algorithm each time you wanted to update, change, or alter it in any way. However, with the strategy pattern, you can introduce a sorting "strategy" into the client, and substitute in different sorting algorithms designed elsewhere. 

A triangular-shaped arrow (like --[ >) is eqiuvalent to "is-a" or is inheritance, whereas a normal arrow like --> is equivalent to "has-a" or composition

![Inheritnace](../images/strategy-pattern.jpeg?raw=true "Inheritance Example")
This is an example of inheritnace, and a better use would be composition

- What if you wanted to add a `fly()` method to `Duck`? Then you'd have to go in and actually implement `fly()` for both the `Wild Duck` and `Mallard`.
    - What if then you added a new type of `Duck`, `Rubber Duck`? You'd still have to implement `fly()` because `Rubber Duck` is a `Duck`, and the implementation of `fly()` in this case would just be to do nothing, since rubber ducks can't fly.
- What if you added two more types of `Duck`s, a `Mountain Duck` and a `Cloud Duck` that fly the same way? Since both `Mountain Duck` is a `Duck` and `Cloud Duck` is a `Duck`, you have to implement the same exact `fly()` implementation for each duck.
    - A solution to this may be to use multiple inheritance, where you could have a `FlysLikeA` is a `Duck` class, and then `Mountain Duck` is a `FlysLikeA` is a `Duck` and `Cloud Duck` is a `FlysLikeA` is a `Duck` 
        - But then, if you have `MountainDuck` is a `FlysLikeA` is a `Duck`, you decide down the line you want to add an `eat()` method to the base `Duck` class, and `Mountain Duck` and `Mallard` eat the same way... how would you "connect" the two without code re-use and overcomplicating things

Inheritnace is good for hierarchical structures, it works as long as behavior is shared downwards. Sharing data horizontally is a huge problem. "The solution to problems with inheritance is not more inheritance." 

We have an algorithm for `display()`, an algorithm for `fly()`, etc... We want to _decouple_ these algorithms and get rid of the hierarchical structure. `Mallard`, `Wild Duck`, etc are all clients that make use of different algorithms for quacking and flying. We need to create **strategies** for quacking, flying, etc.

**Essentially, composition is using interfaces** (whereas inheritance is using abstract classes)
- It's the difference of "implements" (composition) versus "extends" (inheritance)

The end goal here is that you don't have to have `Mallard` and `Cloud Duck` and `Wild Duck` - you can just have `Duck` which implements a combination of multiple interfaces. 

![Composition](../images/strategy-pattern-2.jpg?raw=true "Composition Example")

This leads into the idea of **dependency injection**: you don't hardcode the methods into `Duck` class itself, you inject those depdencies into the class and call them.

At a high level, depdency injection is simply stating that you supply depdencies of another object before compiling.

NO DEPENDENCY INJECTION:
```java
public class TextEditor {
    private SpellChecker spellChecker;

    public TextEditor() {
        spellChecker = new SpellChecker(); // 
    }
}
```

```golang
type TextEditor struct {
    SpellChecker Spellchecker
}

func NewTextEditor() {
    t := new(TextEditor)
    t.SpellChecker = new(SpellChecker(...))
    return t
}
```

Let's take this back to our Duck example:
```java
public class Duck {
    private IFlyStrategy flyStrategy;
    private IQuackStrategy quackStrategy;

    public Duck() {
        flyStrategy = new IFlyStrategy(...)
        quackStrategy = new IQuackStrategy(...)
    }
}
```

```golang
type Duck struct {
    var FlyStrategy IFlyStrategy
    var QuackStrategy IQuackStrategy
}

func NewDuck() Duck {
    d := new(Duck)
    d.FlyStrategy = new(IFlyStrategy(...))
    d.QuackStrategy = new(IQuackStrategy(...))
    return d
}
```

Here, you're hardcoding in which type of flying and quacking strategy your duck has. Therefore, if you want to have multiple types of Ducks, you need to have multiple classes of `Mountain Duck`, `Mallard`, etc...

With dependency injection, this looks like:
```java
public class Duck {
    private IFlyStrategy flyStrategy;
    private IQuackStrategy quackStrategy;

    public Duck(IFlyStrategy flyStrategy, IQuackStrategy quackStrategy) {
        this.flyStrategy = flyStrategy;
        this.quackStrategy = quackStrategy;
    }

    public void fly() {
        this.flyStrategy.fly();
    }
}
```

```golang
type Duck struct {
    FlyStrategy IFlyStrategy
    QuackStrategy IQuackStrategy
}

func NewDuck(flyStrategy IFlyStrategy, quackStrategy IQuackStrategy) {
    d := new(Duck)
    d.FlyStrategy = flyStrategy
    d.QuackStrategy = quackStrategy
    return d
}

func (d *Duck) fly() {
    return d.FlyStrategy.Fly()
}
```

The idea is that wherever in your codebase you're dealing with Ducks, things becomes very interchangeable and modular. Now, you can have something like:

```golang
...
flyStrategy := new(SimpleFlyStrategy) // SimpleFlyStrategy implements IFlyStrategy by implementing Fly()
quackStrategy := new(SimpleQuackStrategy) // SimpleQuackStrategy implements IQuackStrategy

duck := NewDuck(flyStrategy, quackStrategy) // This is totally interchangeable, and Duck becomes decoupled from its strategies
```

We're injection the dependencies. Java is more longwinded than this, where you should (in principle) have a generic `DatabaseDAO.java` interface, and then whenever you instantiate that, you inject either `PostgresDbDao.java` or `DyanmoDbDao.java` class - we're injecting specific things into a more generic interface in the space where the interface is declared, so that in that space we have no dependence on whatever is implemented. `PostgresDbDao implements DatabaseDAO` as a whole long separate class in Java is just a longer way of doing having a class `SimpleFlyStrategy.go` that implements `Fly()` - it's implementing `IFlyStrategy.go` interface but without explicitly saying so, which is just a language thing.

This is how we separate business logic from ports/adapters in a hex (or ports/adapters architecture). In the business logic, we just run something like 
```golang
type repository interface {
	Add(i int)
	Subtract(i int)
}

db := new(postgres.PostgresDbDao)

// someservice.NewService(r repository) takes in an interface r that must implement Add and Subtract
// so as long as postgres.PostgresDbDao implements Add and Subtract, we can inject it and continue our business logic as normal
someService := someservice.NewService(db)
```

This is **constructor-based** dependency injection, and here the `Duck` container invokes a class constructor with a number of arguments, each representing a depdency on the other class. This can similarly be done with **setter-based dependency injection**, which is accomplished by the container calling a setter method.
- In Spring, setter methods are called on beans after invoking a no-argument constructor or no-argument static **factory** method to instantiate the bean.

You use interfaces here, because say you had two implementations of fly and quack `SimpleFly implements IFlyStrategy`, `FastFly implements IFlyStrategy`, `SimpleQuack implements IQuackStrategy`, `LoudQuack implements IQuack Strategy`. You could do something in another class like:

```java
...
Duck duck = new Duck(SimpleFly, LoudQuack);
...
```

Now, instead of having to hardcode each type of duck, you have a specific duck for this use case.

![General](../images/strategy-pattern-3.jpg?raw=true "General Example")