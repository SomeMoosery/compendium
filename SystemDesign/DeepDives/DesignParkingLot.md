# Design a Parking Lot:

## Clarify Ambiguity...
If the interviewer says something like "Your task is to design a parking lot," he's leaving it purposely ambiguous in hopes that you'll ask some clarifying questions, since this can actually lead to a number of different types of problems.

1. Do you want me to come up with a system design? 
2. Do you want me to come up with a class hierarchy? 
3. Should we get into certain specific facets and write some functions around them? 

## Show a Clear, Systematic Approach to Tackling the Problem...
What does this parking lot actually look like? 

1. Is it a parking garage (a building i.e. is it multi-leveled)? An open space? 
2. Are some parking lots only accessible if other parking lots are free? 
3. How many spots are in the parking lot (scalability)? 
4. Are there multiple entrances (concurrency)?
5. Should we optimize to fill certain areas first (optimization)?
6. Pricing - if there's multiple sizes of spots, should they be charged differently? Based on location? 

## These assumptions were cleared up, now the actual problem/process...

**Given:** There's 4 sizes of lots (S (motorcycles), M (compact cars), L (standard cars), XL (buses))
**Given:** We want to know which cars to put in which spots
**Given:** We can put smaller cars into bigger spots (i.e. we can put a M car into L or XL spot), but not vice versa

**Given:** We want a class hierarchy...

So, we know that our main problem is a parking lot, so we want a `ParkingLot`, we want vehicles `Vehicle`, and the spots `Spot`

Take vehicles first. A lot of vehicles have similar attributes, so we'd probably want some kind of abstract class or interface for vehicles, where we narrow it down later

```java
public enum Color {
    RED, BLACK, WHITE, SILVER;
}
public abstract class Vehicle {
    String licensePlate;
    Color color;
}
```

![Vehicle Class Diagram](images/parking-lot-class-diagram.png?raw=true "Vehicle Class Diagram")

**NOTE:** We might not want to have a String store the size of the car. It could be an enum, and int, whatever you want here to fit the specifications of the rest of the problem.

You now have 4 vehicles that inherit from this vehicle.

We also, importantly, need some sort of a big system which has a method that places a car in the parking lot. What methods might this class need?
1. When a driver pulls into the parking lot and wants to park his car there
    - We need a method called `placeVehicle()`
    - What should this method take in as a parameter? Probably a vehicle, `Vehicle`.
    - What should this method return? It looks like it should return some other class `ParkingSpot`
2. We now see that we have the need for a `ParkingSpot` class as well
    - We'd want to link this `ParkingSpot` to the `Vehicle` currently in it as well
    - We need to know what size the spot is. We could use an int, a String, but why not an enumeration? 
    - Depending on the assumptions before, we should mention what level it's on, etc...

```java
class ParkingLot {
    String zipCode;

    public ParkingSpot placeVehicle(Vehicle vehicle)
    public ParkingSpot removeVehicle(Vehicle vehicle)
}

class ParkingSpot {
    long id;
    Size size;
}

enum VehicleSize {
    SMALL, MEDIUM, LARGE, XLARGE;
}
```

At this point, we have most of the very basics of the parking lot settled. Now, the interviewer may get into more of a deep dive on some of the algorithmic parts of this system. So, what is our goal? 

For example, the goal is to make the placing and retrieving of the car as efficient as possible.
- How do we store this kind of information? 
    - In reality, there's some **database backend** that stores all info - where cars are, which spots we have, where parking lots are, etc...
    - But, this isn't reality. Abstract that away - assume all this information is in memory

We need to store this information in the right kind of data structure. **We need to think about how to retrieve the first spot which is suitable for the kind of car we're getting**.

1. We're passed in a specific vehicle, say a `Truck`. If we used an array, we'd have to go through each `ParkingSpot` until we find one that can fit a `Truck` and is also available.
2. Since we're more concerned about time, we aren't as concerned about space. So, let's have 4 Stacks, one for each size spot that contains only available spots. This is O(1) because the worst case is we have to do 4 lookups (if we have a motorcycle and there are no free spots, we have to check if the smallStack, mediumStack, largeStack, and xLargeStack are all empty)
3. Thinking about removing the `Vehicle`, once we place it, we need to put that into some structure that has all the taken spots. Since we want O(1) lookup, this is clearly a HashMap, which gives us an O(1) placement.
4. For removal, the method would simply be looking up the vehicle in the HashMap, removing it from the map, and adding it back into the appropriate Stack of free spots.

## Edge Cases
1. Concurrency: race conditions if you have multiple entrances and two cars end up trying to park in the same location
2. Pricing Strategies: does the customer pay the same price for the same spot, or does he pay more for bigger spots?

**KEY:** Ask questions, take hints and run with them