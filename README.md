# I'll sleep when I'm rich
Decided to compile all the resources I've used intermittently for Leetcode prep over the years into one place so this time I can go in and absolutely dominiate this part of the interview, which is usually my weakest point.

## Definitions:
* **Pass by reference**: you pass the actual memory reference of the object into the following function. Whatever you change in that function changes the actual value of the object. If you pass a CoffeeCup object into a fill(CoffeeCup) method from main(), the CoffeeCup object in main will be filled up
* **Pass by value**: you pass in a copy of the object into the following function. If you pass a CoffeeCup object into a fill(CoffeeCup) method from main(), the CoffeeCup object in main is not filled up.

## General Tips & Tricks:
* If you want to pass an int (**or any primitive type**) by reference in Java, you inherently cannot. What you *can* do, however, is create a 1-element array to hold the value of that primitive, and pass that by reference

```
public static void main(String[] args) {
    int[] count = new int[]{0};
    for (int i = 0; i < 10; i++) {
        tick(count);
    }
}

private void tick(int[] count) {
    count[0]++;
}
```
