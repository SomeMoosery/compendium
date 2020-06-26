# React

**Reactivity** is all about data flow, tracking values through your application
- When a value changes, your application 
- The essence of functional reactive programming is to specify the dynamic behavior of a value completely at the time of declaration

![Example React Architecture](images/react-architecture-1.png?raw=true "Example React Architecture")

![Example React Architecture](images/react-architecture-2.png?raw=true "Example React Architecture")

![Example React Architecture](images/react-architecture-3.png?raw=true "Example React Architecture")

## The Virtual DOM

Most JavaScript libraries update the DOM a lot more than they need to
- for example, if you have a list of 10 items and check off one item, most JS frameworks would rebuild the _entire_ list!

In React, for every DOM object, there is a correspodning **virtual DOM object:** a representation of a DOM object (like a lightweight copy)
- this has the same properties of a real DOM object, without the capability to directly change what's on the screen
- manipulating the virtual DOM is like manipulating a blueprint, whereas manipulating the actual DOM is actual rearranging the furniture

1. When you render a JSX element, every single virtual DOM object gets updated 
2. Once the virtual DOM has updated, React comares the virtual DOM with a virtual DOM snapshot that was taken right before the update. This allows React to figure out exactly what was changed on the real DOM.
3. Once React knows exactly which objects changed, it updates the real DOM (so, in this case, only the checked-off box would be updated)

![Virtual DOM Flow](images/virtual-dom.png?raw=true "Virtual DOM Flow")

## Hooks



### Problems

- Every time there is any state change at all, you need to redeclare functions that close over local state
- The virtual DOM is inefficient
    - You have to look through each element on the page to see what's changed. This is an improvement over reloading the entire page, but is still extremely inefficient 
    - virtual DOM is really just not fast
- React doesn't have any knowledge of the values flowing through your app, it treats it like a black box
- Things like `useCallback()` are an **abstraction leak** that let you tell the computer that it doesn't need to worry about this subtree within your application (you're doing the thinking for the computer)