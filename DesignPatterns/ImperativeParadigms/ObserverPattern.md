# Observer Pattern

Essentially pub-sub, one "thing" A wants to know when the state of another "thing" B changes
- Moves from "poll" to "publish." Thing A doesn't have to continuously poll Thing B to see if it changes. Thing B publishes that "I've changed," and Thing A is subscribed to that. This clearly doesn't scale...

More formally, it's a one-to-many relationship such that when the state of the one changes, the many are notified

## In practice

Two interfaces:
- `IObservable` (1)
- `IObserver` (N)

```golang
type IObservable interface {
    // Add adds an observer to the observable, implemented however (list, array, etc)
    Add(IObserver o)

    // Remove removes an observable from the observable, implemented however
    Remove(IObserver o) 

    // Notify actually notifies the observer of a state change
    Notify()
}
```

```golang
type IObserver interface {
    // Update is called when the observable runs Notify()
    Update()
}
```