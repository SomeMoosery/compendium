# Elliptic Curve Cryptography

The basics is the function $y^2$ = $x^3$ + ax + b

Bitcoin, Ethereum, and others use SECP256K1: $y^2$ = $x^3$ + 7

## Point Addition

You can add points on an elliptic curve together, to get another point.

1. Find the line that goes through those two points
2. Determine where the line intersects the curve at the third point
3. Reflect the third point across the x-axis, and the result is the sum of the first two points.

To do ECC more efficiently, rather than adding two arbitrary points together, you specify a base point on the curve and add that point to itself

1. Choose a point P (or, 1xP)
2. Take the tangent line of that point
3. Find where the tangent intersects the graph a second time, reflect it over the x-axis, and now you have $P^2$ or 2xP

A principle of point addition is that nP + rP = (n+r)P

**Ex:** 4P + 6P = 10P. So to calculate 10P, just do P + P = 2P -> 2P + 2P = 4P, 4P + 4P = 8P, 2P + 8P = 10P 
- Computing xP (where x is an integer) would never cost more than 510 steps. x goes up to $2^255$. 

**NOTE: you cannot divid on an eliptic curve**

## Private nad Public Keys

Since you cannot divide in ECC, it makes sense to have your key-pair be pk = sk*P. You can never calculate sk, because you can't do pk / P.
- sk is a random 256-bit integer
- pk is a point on the elliptic curve

## To create a bitcoin / eth address
1. generate random 256-integer sk
2. generate pk = sk * P using the parameters for the SECP256K1 curve
3. The hash of the pk is an address