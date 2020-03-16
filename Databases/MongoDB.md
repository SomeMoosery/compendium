# MongoDB Information

## Running a local instance of Mongodb

### Install
Ensure you already have Homebrew

1. `brew tap mongodb/brew`
2. `brew install mongodb-community@4.2`

### Start Local MongoDB Community Edition

1. `brew services start mongodb-community@4.2` (just change `start` to `stop` to stop)
2. Confirm it's running with `ps aux | grep -v grep | grep mongod`
3. Run with MongoDB CLI with `mongo`

### Using the CLI

You can also use [MongoDB Compass](https://www.mongodb.com/download-center/compass)

MongoDB allows you to switch to non-existing databases. Use `use <DBNAME>` to switch to a database. The database isn't created until you actually store data in the database. For example:
- `use myNewDatabase` - no database has been created yet
- `db.myCollection.insertOne( { x: 1} );` - creates the database `myNewDatabase` and initializes colletion `myCollection` with `x: 1`

You can see all available databases with `show dbs`

**Commands:**
- `db` - shows the database you're currently in
- `show dbs` - shows all databases you have locally
- `db.getUsers()` - shows all users in all databases
- `dp.dropUser("<USERNAME>")` - deletes a user
- `db.createUser( { user: "<USERNAME>", pwd: "<PWD>", roles: [ { role: "<ROLE>" } ] } )` - creates a new user in current database
    - see [here](https://docs.mongodb.com/manual/reference/method/db.createUser/) for more documentation