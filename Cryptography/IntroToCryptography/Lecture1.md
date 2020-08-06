# Introduction to Cryptography

Cryptology (just a small part of security)
- Cryptography: the people who encrypt stuff
- Cryptanalysis: this is the people who try to break these encryptions 

Cryptography:
1. Symmetric Algorithms 
2. Asymmetric Algorithms (Public Key)
3. Protocols

**encryption** is just a mathematical formula that changes x into y

![Basic Example](./images/1.png?raw=true "Basic Example")

## Notation:
- x = plaintext
- y = ciphertext
- e = encryption function
- d = decryption function
- k = key
- |k| = key space = number of keys

## Setup for Symmetric Cryptography
**Basic Problem:** Communication over insecure channel
- channels = the internet, airwaves GSM, etc
    - The internet is an **insecure** channel over which people can send messages

For thousands of years, people thoguht it was the best idea to keep encryption algorithm `e` and decryption algorithm `d` private, secret, because if anyone can access `e` or `d`, then anyone can use `d` to decrypt a message encrypted with `e`.
- BUT, it's very easy to think you have a very secure `e` and `d`... but you probably don't
- The only way to ensure that your encryption algorithm is secure is to **make it public**, hoping that cryptoanalytical people break your encryption to point out flaws and vulnerabilities.
- It's difficult to prove algorithms work right away, or ever, you need to test it against real people to see if it's breakable.

**Never use an untested crypto algorithm**

Although, if we have public encryption and decryption algorithms, what's to stop Oscar from taking the stolen encrypted message `y`, taking the public decryption algorithm `d`, and decrypting the message `x`?
- **This is why we need keys**

![Basic Example](./images/2.png?raw=true "Basic Example")

You can still break this, technically, with a brute force attack. Imagine if you only had 1000 possible keys - you could easily guess the key
- We need to pass the keys through a secure channel!

![Basic Example](./images/3.png?raw=true "Basic Example")

**Kerckhuffs' Principle (1883):** A cryptosystem should be secure even if the attacker (Oscar) knows all the details about the system, with the exception of the secret key 

## Substitution Cipher
- historical cipher 
- operates on letters (not bits)

**Idea:** Replace every plaintext by a fixed ciphertext letter
- i.e. A->L, B->d, etc...
- e(ABBA) -> LddL

### This is obviously a shitty cryptosystem... but how can we break it?
1. **Attack:** Brute-force attack or exhaustive key search
- For the letter A, we have 26 possibilities of a cipher letter, for letter B we have 25, etc etc... it becomes a matter of `26!` = `2^88` guesses to break the encryption. This isn't possible to do.
    - keyspace is too large, cannot run a brute-force attack
2. **Attack:** Letter Frequency Analysis
- The letter `E` is used approximately every 3 letters in the English language. If we see that 30% of our letters in the ciphertext is `q`, it's likely that E->q in the cryptosystem - we immediately have 30% of our cryptosystem deciphered
- This works becuase identical plaintexts map to identical ciphertext symbols 

## Classification of Attacks
There are often many possible attack approaches (**"attack vectors"**)

### cryptanalysis
- **classical cryptanalysis**:
    - Brute force
    - Analytical attack (like letter frequency analysis)
- **social engineering**
- **implementation attacks**
    - Side Channel
            - Example: Take the EMV Chip of your credit card (which is really just a tiny computer that runs encryption), hook it up to an oscilloscope, read the wavelenghths, and decipher the secret key from that
