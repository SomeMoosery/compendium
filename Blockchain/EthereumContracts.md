**NOTE: notes are based largely on the [Crypto Zombies](https://cryptozombies.io/en/) course**

## Concepts
State variables are permanently stored on-chain, so they can be used like an on-chain database. As such, they are expensive. See definition below

When calling another contract, we need to first create an interface for that contract within our own contract. So, if we had a contract we wanted to call: 

```solidity
contract LuckyNumber {
  mapping(address => uint) numbers;

  function setNum(uint _num) public {
    numbers[msg.sender] = _num;
  }

  function getNum(address _myAddress) public view returns (uint) {
    return numbers[_myAddress];
  }
}
```

We would have to define 

```solidity
contract NumberInterface {
  function getNum(address _myAddress) public view returns (uint);
}
```

within our own contract first. We have to create the interface that the contract inherits.

**Pass by value** is when the Solidity compiler creates a new copy of the parameter's value and passes it to your function. This allows your function to modify the value without worrying that the value of the initial parameter gets changed. **Pass by reference** means that the function is called with a reference to the original variable. Thus, if your function changes the value of the variable it receives, the value of the original variable gets changed.
- using the `memory` keyword passes by value and is necessary for all reference types like arrays, structs, mappings, strings

Memory arrays can't be resized like storage arrays can

Oftentimes, looping over an array and recreating it within a function as a `memory` array is cheaper than updating a `storage` array, as the `memory` array doesn't actually need a blockchain transaction.
- Rebuilding an array in memory every time a function is called instead of saving that array in a variable is actually more efficient when dealing with Ethereum.

It's _convention_ to start function param variable names with an underscore (i.e. `_var`) in order to differentiate from global variables

Modifiers (`modify`) are half-functions used to extend functions, usually ways to check/throw errors:
```solidity
modifier onlyOwner() {
  require(isOwner());
  _;
}

function renounceOwnership() public onlyOwner {...}
```

^ The `_;` is the terminator, which transfers execution back to the function, not the modifier 

Functions are public by default (anyone, or any other contract, can call your contract's function and execute its code)
- you should make functions `private` by default
- `private` functions should be prefaced with an underscore like function parameters (i.e. `_createPerson(...) private {...}`)

Constructors are run only once - _on the creation of the contract_

**Pure functions** should be marked as `pure`

**View funcions** should be marked as `view`
- VIEW FUNCTIONS DON'T COST GAS since they only read data and don't actually change anything on the blockchain
- making a function `view` tells `web3.js` that it only needs to query your local Ethereum node to run the function, it doesn't need to create a transaction on the blockchain (which would therefore need to be run by every single node, and cost gas)

Strings, arrays, structs, mappings still need `memory` in return value (i.e. `returns (string memory)`)

In Solidity, function execution must always start with an external caller. There will always be a `msg.sender`

You can add `payable` functions, but you need to also add a way to withdraw the ether, otherwise it stays stuck in the contract's account

`uintN`s are always 2 ^ N. So `uint16` is 2 ^ 16 = max 65536, `uint8` = 256, etc... think about this when creating variables. You wouldn't want a daily ticker to be a `uint8` because it would overflow after only 256 days.

The `approve(address _approved, uint256 _tokenId)` function allows not just the owner, but whoever the owner approves, to call `transferFrom(address _from, address _to, uint256 _tokenId)`.

Using `library` instead of `contract` makes the library `use`able, meaning that you can use, for example, `using SafeMath for uint;` and then call `uint test = 2` and `test = test.mul(3)` and it'll work.

We should always use [OpenZeppelin SafeMath](https://docs.openzeppelin.com/contracts/2.x/api/math#SafeMath) instead of built-in `uint` functionality (so instead of `++` we should always use `.add(1)`)

Comments should follow [NatSpec comment format](https://docs.soliditylang.org/en/v0.5.10/natspec-format.html)

`call()` in Web3.js is used for `view` and `pure` functions, `send()` is used for any other function (equivalent to GET vs POST)

## Global variables / functions / modifiers
* `msg.sender`: the address of the person (or smart contract) which called the current function
* `emit` emits an `event` to the frontend
* `require` makes it so that the function will throw an error and stop executing if some condition is not true
* `now` is just the current UNIX time
* `seconds`, `minutes`, `hours`, `days`, `weeks`, `years` all convert to a `uint` number of seconds of that length of time
* `payable` means that you can send ETH along with the request
* `ether` is a number amount of either (i.e., check `msg.value == 0.001 ether`)

## Definitions:
**Contract:** the fundamental building block of Ethereum applications — all variables and functions belong to a contract, and this will be the starting point of all your projects

**ABI:** Application Binary Interface - a representation of your contracts' methods in JSON format that tells Web3.js how to format function calls in a way your contract will understand 

**Storage (pointer):** variables stored permanently on the blockchain (like a hard disk, database). _State variables are `storage` by default_!!
- You can only use struct pointers in private/internal functions

**Memory:** variables that are temporary, erased between external function calls to your contract (like RAM). These variables are declared within functions.

**Overflow:** a typical byte overflow, for example when you have a `uint8 var = 255` and run `var++`, you get `0`, not `256`. 
- use [OpenZeppelin SafeMath](https://docs.openzeppelin.com/contracts/2.x/api/math#SafeMath) library to avoid this.

**State Variables:** variables permanently stored in contract storage i.e. written to the Ethereum blockchain. These variables must be declared outside of functions. **This is like a database write** (i.e. they're expensive)
- Creating a dynamic array of structs can be useful for storing data in your contract, **kind of like an on-chain database**
- Adding `public` in the variable definition generates a `getter()` function and allows other contracts to read from (but not write to) that variable

**Pure functions:** functions that do not read from state and only deal with function parameters

**View functions:** functions that don't change any values or write anything

**Event:** a way for your contract to communicate that something happened on the blockchain to your app front-end, which can be "listening" for certain events and take action when they happen

**Internal:** The same as `private`, except that it's also accessible to contracts that inherit from the declaring contract

**External:** The same as `public`, except that it can ONLY be called outside the contract (not by other functions inside that contract)

## The "efficiency paradox"

Say you were building a trading card game, and wanted to create a `getCardIdsByOwner()` method. The naiive approach would be to simply create a storage mapping `mapping (address => uint[]) public ownerToCardIds`.

Every time you created a new card, you could simply use `ownerToCardIds[owner].push(cardId)` to add taht card ID to the owner's array. Therefore, `getCardIdsByOwner` would just be:

```soliditiy
function getCardIdsByOwner(address _owner) external view returns (uint[] memory) {
  return ownerToCardIds[_owner];
}
```

But... what if we wanted to add functionality where you could transfer ownership (trade) cards between owners. That function would need to
1. Push the card to the new owner's `ownerToCardIds` array
2. Remove the card from the old owner's `ownerToCardIds` array
3. Shift every card in the old owner's array up one place to fill the hole
4. Reduce the array length by 1

All of these steps would be expensive, but step 3 would be heinous. You'd have to do a write for every card whose position we shifted. If an owner has 20 cards and trades away the first one, we'd have to do 19 writes to maintain the order of the array.

Since `view` functions don't cost gas when called externally, we can use a for loop to iterate the entire `cards` array and build an array of cards that belong to the specific owner. That way, you don't need to reorder any arrays

```solidity
function getCardIdsByOwner(address _owner) external view returns (uint[] memory) {
  uint[] memory result = new uint[](ownerToCardIds[_owner]);
  uint counter = 0;

  for (uint i = 0; i < cards.length; i++) {
    if (cardToOwner[i] == _owner) {
      result[counter] = i;
      counter++;
    }
  }

  return result;
}

This is computationally slower, but financially cheaper to run.
```

## Smart Contract Structure
* Every smart contract needs a _version pragma_ i.e. a version declaration 

```solidity
pragma solidity >=0.5.0 <0.6.0;

contract HelloWorld {

    // event
    event NewPerson(uint personId, string name, uint dna)

    // struct
    struct Person {
        uint age;
        string name;
    }

    // map
    mapping (uint => address) public map;

    // state varible. This will be stored permanently in the blockchain
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
    function createRandomPerson(string memory _name) public {
        uint randDna = _generateRandomDna(_name);
        _createPerson(_name, randDna);
    }
}
```