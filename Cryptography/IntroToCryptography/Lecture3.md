# Stream Ciphers, Random Numbers, and the One Time Pad

## Intro to Stream Ciphers

Within symmetric cryptography, there are:
1. Stream ciphers
2. Block ciphers

**Motivation:** Cell phones
1. A message is encrypted $e_k$(x) = y
2. y is sent over cell network to cell tower
3. Cell tower runs $d_k$(y) = x

The encryption and decryption is done with a stream cipher

**Stream cipher:** a stream cipher encrypts bits individually (bitwise) as opposed to block ciphers (which take 128 bits of plaintext at a time)
- encryption: $y_i$ = e($x_i$) = $x_i$ + $s_i$ mod 2 (s is the key, not k)
- decryption: $x_i$ = d($y_i$) = ($y_i$) + $s_i$ mod 2

**Question:** Why are decryption and encryption the same? 

d($y_i$) ≡ $y_i$ + $s_i$ mod 2
         
≡ ($x_i$ + $s_i$) + $s_i$ mod 2

≡ $x_i$ + 2$s_i$ mod 2..... but 2 mod 2 = 0 therefore 2$s_i$ will always be 0

≡ $x_i$

**NOTE:** $x_i$, $y_i$, $s_i$ ∈ $ℤ_2$ (i.e. {0,1})

=> mod 2 addition and subtraction are the same operation
- It doesn't matter if you add a value mod 2, or subtract a value mod 2... it's the same operation
- so you could technically say $x_i$ = d($y_i$) = ($y_i$) - $s_i$ mod 2, but it's not mathematically correct