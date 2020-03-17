# Gaia

Gaia is the key-value store associated with an individual's Blockstack account. Whenever an account is made, the user gets a certain amount of "free" Gaia storage (where the data is hosted across the decentralized storage network), or they can host their own Gaia hub.

# Radiks

Radiks is basically the solution to query all of the decentralized data for each user stored in Gaia. Since data in Gaia is decentralized, there technically isn't a way to do this easily. The solution Radiks provides is a "centralized" version of Gaia data. 

Radiks provides an API for the user to create an abstraction of Gaia data in the form of MongoDB Collections.

Using Radiks, when a collection/model is created while a user is signed in, this data is stored in two places. The raw data is stored as per usual to the user's Gaia storage. **BUT**, the data is then signed with a secret key associated with the user's credentials, and stored in a MongoDB database/cluster. That way, you're able to query this data through MongoDB, unencrypt it, and then 

## Including Radiks in-app

Create a new model like like, ideally in a separate `/models` directory:

```javascript
class Todo extends Model {
  static className = 'Todo';
  static schema = { // all fields are encrypted by default
    title: String,
    completed: Boolean,
  }

  // Optional defaults...
  static defaults = {
      title: "No Title Given"
  }
};
```

Then, in a portion of you code (like `ComponentDidMount`) check that the user is logged in `userSession.isUserSignedIn()`, and you can do the following commands:

## Commands:

Create and add a new item to the collection:

```javascript
const todo = new Todo({ title: 'Use Radiks in an app' });
await todo.save()
```

Update that same item:

```javascript
todo.update({
  completed: true,
});
await todo.save();
```

Get all the items you've created under the user:
- You can add parameters to `fetchOwnList({ completed: false })` to query on the data you've made

```javascript
const allTodos = await Todo.fetchOwnList()
```

Query a model:

`const result = await Todo.fetchList({ completed: true });` - all the complete Todos created across all users

Get the count of a certain condition:

`const count = await Todo.count({ completed: true })` - number of completed todos

Delete an entry:

`todo.destroy()`

### Notes

I'm not sure how you delete users or do this with the command line. What I had done was, to test I could both create users, and then deleting them so as to not clog things up, I ran the following after ensuring we were logged in: 

```javascript
const patient = new Patient({
    doctor: "Test Doctor",
        location: ['123', '456'],
    })
    await patient.save()
    const allPatients = await Patient.fetchOwnList()
    console.log('ALL PATIENTS:', allPatients)
    var p;
    for (p of allPatients) {
        p.destroy()
    }
}
```