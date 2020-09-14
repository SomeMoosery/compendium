# Modular Arithmetic and Historical Ciphers

**GOAL:** Computation in finite sets 
- i.e. infinite sets being real numbers, integers, etc... 
- modern crypto is dealt with FINITE sets, though... you need special computational rules for this

**Example:** for a finite set in everyday life...
- If you go to the bakery and order 20 loaves of bread, and then order 12 more, you'll have 32 loaves of bread
- But, 20 hours from 12 o'clock isn't 32 hours, it's 8 o'clock
- This is the basis of modular/modulo arithmetic. It goes around "in circles." When the finite set goes up to 24, 20+12=8, 32 % 24 = 8

**_(Def)_: modulo operator:** Let `a`, `r`, `m` ∈ `ℤ` and `m > 0`, we write `a` ≡ `r mod m`. If `m` divides `(a-r)`, i.e. `m / (a-r)`.

**Example:** a = 13, m = 9, r = ?

9 ≡ (13 - r), r = 4

## 1a. Computation of the remainder 
Given: `a, m ∈ ℤ`, write `a = q * m` (`q` being quotient)

**Example:** a = 42, m = 9

42 = q*9, q = 4, so r = 6

but also...

42 = 3 * 9 + 15, and in this case r = 15. **BUT, does this hold in the actual definition?**

Check (42-15) = 27, 9 / 27 checks out

but also......

42 = 5 * 9 + (-3), and in this case r = -3 **BUT, does this hold in the actual definition?**

Check (42 + 3) = 45, 9 / 45 checks out

### LESSON: The remainder is not unique!!

## 1b. Equivalence Classes
**Example:** a = 12, m = 5, 12 ≡ 2 mod 5 (check 5 / (12-2)), 12 ≡ 7 mod 5 (check 5 / (12-7))

Def'n the set `{..., -8, -3, 2, 7, 12, ...}` forms an **equivalence class** modulo 5. All members of the class behave equivalent modulo 5

Let's look at all equivalence classes modulo 5...

`{..., -10, -5, 0, 5, 10, ...}` A

`{..., -9, -4, 1, 6, 11, ...}` B

`{..., -8, -3, 2, 7, 12, ...}` C

`{..., -7, -2, 3, 8, 13, ...}` D

`{..., -6, -1, 4, 9, 14, ...}` E

Let's say you have to do a computation: `13 * 16 - 8` = `208-8` = `200 ≡ 0 mod 5`. But, we can think of this as `D * B - D`... you can use **ANY OTHER MEMBERS OF THE EQUIVALENCE CLASS**. This is the same thing as `3 (i.e. 13 mod 5) * 1 (i.e. 16 mod 5) - 3 (i.e. 13 mod 5)` = `3-3` ≡ 0 mod 5

**Example:** $3^8$ mod 7 ≡ ? - this is what HTTPS does (but the numbers are 200 bits long)
- This could be done as $3^8$ = 6561 ≡ 2 mod 7
- This could done more easily as $3^8$  = $3^4$ * $3^4$ = 81 * 81 **BUT YOU CAN MAKE EQUIVALENCE CLASSES FOR MODULO 7 AS ABOVE TO GET A MUCH SMALLER NUMBER!!** So you get `4 (i.e. 81 mod 7) * 4 = 16 ≡ 2 mod 7`

**By convention, you'll usually be multiplying by a maximum of 4, as the smallest members of equivalence classes will usually be `{1, 2, 3, 4}`**

### LESSON: equivalence classes are interchangeable!!

## 2. Rings: An algebraic view on modular arithmetic 
The "integer ring" $ℤ_m$ consists of:
1. The set $ℤ_m$ = {0, 1, ..., m-1}
2. Two operators `+` and `*` such that for all a, b, c, d ∈ $ℤ_m$

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; i. a + b ≡ c mod m

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ii. a * b ≡ d mod m

**Example for multiplicative inverses:** m = 9 (i.e. $ℤ_m$ = {0, 1, 2, 3, 4, 5, 6, 7, 8}), a = 2, $a^{-1}$ = ?
- 2 * $2^{-1}$ ≡ 1 mod 9. So, 2 * 5 ≡ 1 mod 9, so $2^{-1}$ ≡ 5 mod 9
- Or, you could do something random... 6 * ? ≡ 1 mod 9... compute _gcd_(6,9) = 3 != 1, so the inverse does not exist

i.e. the nature of rings is:
- you can always add and subtract
- you can always multiply 
- taking the inverse (or division), only works sometimes

## 3. Shift (or Caesar) Cipher
**Idea:** Shift letters in alphabet 

**Example:** k = 3
- A -> d
- B -> e

...

- W -> z
- X -> a
- Y -> b
- Z -> c

Wrapping around at the end of the alphabet is done nicely with modulo 26!

**Encryption:** $e_k$(x) = x + k mod 26

**Decryption:** $d_k$(y) = y + k mod 26

**EXAMPLE:** k = 17. ATTACK = $x_1$,$x_2$,...$x_6$ = 0,19,19,0,2,10. This becomes 17,10,10,17,19,1 or rkkrtb

Two attacks possible:
1. Frequency analysis 
2. Brute force

## 4. Affine Cipher

k = (a, b) -> key space becomes larger
y ≡ a * x + b mod 26

y - b ≡ a * x mod 26

x ≡ $a^{-1}$(y-b) mod 26

#k = ?, #b = 26, condition for a = _gcd_(a, 26)

#k values = (#a values) * (#b values)

**NOTE:** Write s$t^{-1}$ instead of s/t