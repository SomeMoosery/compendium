**NOTE: notes are based largely on the [Crypto Zombies](https://cryptozombies.io/en/) course**

## Concepts
State variables are permanently stored on-chain, so they can be used like an on-chain database. As such, they are expensive. See definition below

**Pass by value** is when the Solidity compiler creates a new copy of the parameter's value and passes it to your function. This allows your function to modify the value without worrying that the value of the initial parameter gets changed. **Pass by reference** means that the function is called with a reference to the original variable. Thus, if your function changes the value of the variable it receives, the value of the original variable gets changed.
- using the `memory` keyword passes by value and is necessary for all reference types like arrays, structs, mappings, strings

It's _convention_ to start function param variable names with an underscore (i.e. `_var`) in order to differentiate from global variables

Functions are public by default (anyone, or any other contract, can call your contract's function and execute its code)
- you should make functions `private` by default
- `private` functions should be prefaced with an underscore like function parameters (i.e. `_createPerson(...) private {...}`)

**Pure functions** should be marked as `pure`

**View funcions** should be marked as `view` 

Strings, arrays, structs, mappings still need `memory` in return value (i.e. `returns (string memory)`)

## Definitions:
**Contract:** the fundamental building block of Ethereum applications — all variables and functions belong to a contract, and this will be the starting point of all your projects

**State Variables:** variables permanently stored in contract storage i.e. written to the Ethereum blockchain. **This is like a database write** (i.e. they're expensive)
- Creating a dynamic array of structs can be useful for storing data in your contract, **kind of like an on-chain database**
- Adding `public` in the variable definition generates a `getter()` function and allows other contracts to read from (but not write to) that variable

**Pure functions:** functions that do not read from state and only deal with function parameters

**View functions:** functions that don't change any values or write anything

**Event:** a way for your contract to communicate that something happened on the blockchain to your app front-end, which can be "listening" for certain events and take action when they happen

## Smart Contract Structure
* Every smart contract needs a _version pragma_ i.e. a version declaration 

```solidity
pragma solidity >=0.5.0 <0.6.0;

contract HelloWorld {

    event NewPerson(uint personId, string name, uint dna)

    struct Person {
        uint age;
        string name;
    }

    // this will be stored permanently in the blockchain
    uint myUnsignedInteger = 100;

    // a public array of People (i.e. kind of like an on-chain database)
    Person[] public people;

    // a private function that passes _name from memory (i.e. by value)
    function _createPerson (string memory _name, uint _dna) private {
        // Emit this action to be handled by a callback in your frontend
        uint id = people.push(Person(_name, _dna)) -1 ;
        emit NewPerson(id, _name, _dna);
    }

    // A private view function that returns a uint
    function _generateRandomDna (string memory _str) private view returns (uint) {
        // generates random hash using built-in keccak256 function, and casts it as a uint
        uint rand = uint(keccak256(abi.encodePacked(_str)));

        // returns 16-digit
        return rand % dnaModulus;
    }

    // A private pure function that returns a uint
    function _multiply(uint _a, uint _b) private pure returns (uint) {
        return a * b;
    }

    // Creates a random person
    function createRandomPerson(string memroy _name) public {
        uint randDna = _generateRandomDna(_name);
        _createPerson(_name, randDna);
    }
}
```