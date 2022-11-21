# Entity

## Creating the Joke Entity
Make the Joke class an `Entity`.

1. In the `database.jokes` package, find and open `Joke.kt`.
2. Create the `Joke` data class with parameters for an ID, a jokeSetup (`String`), a jokeType (`String`) and a punchline (`String`)
```Kotlin
data class Joke(
    var jokeId: Long = 0L,
    var jokeSetup: String = "",
    var jokeType: String = "",
    var punchline: String = "",
)
```
3. Annotate the data class with `@Entity`, and name the table `custom_joke_table`.
```Kotlin
@Entity(tableName = "custom_joke_table")
```

4. Identify the `jokeId` as the primary key by annotating it with `@PrimaryKey`, and set the `autoGenerate` parameter to `true`:
```Kotlin
    @PrimaryKey(autoGenerate = true)
```

5. Annotate the remaining properties with @ColumnInfo and customize their names as shown below.

Your finished code should look like this:

```Kotlin
@Entity(tableName = "custom_joke_table")
data class Joke(
    @PrimaryKey(autoGenerate = true)
    var jokeId: Long = 0L,

    @ColumnInfo(name = "joke_setup")
    var jokeSetup: String = "",

    @ColumnInfo(name = "joke_type")
    var jokeType: String = "",

    @ColumnInfo(name = "joke_punchline")
    var punchline: String = "",
)
```

## DAO - JokeDatabaseDao

1. Create an interface `JokeDatabaseDao` and annotate it with `@Dao`:
```Kotlin
@Dao
interface JokeDatabaseDao {}
```

2. Add an `@Insert annotation`, and an `insert()` function that takes one `Joke`.

```Kotlin
    @Insert
    fun insert(joke: Joke)
```

3. In the same way, add an `@Update` annotation with an `update()` function for one `Joke`:

```Kotlin
    fun update(joke: Joke)
```

4. Add a `@Query` annotation with a function `get()` that takes a `Long` key argument and returns a nullable `Joke`:

```Kotlin
    @Query("SELECT * from custom_joke_table WHERE jokeId = :key")
    suspend fun get(key: Long): Joke?
```

5. Add another `@Query` with a `clear()` function and a SQLite query to delete everything from the `custom_joke_table`:

```Kotlin
    @Query("DELETE FROM custom_joke_table")
    suspend fun clear()
```

6. Add a `@Query` to `getAllJokes()`:

```Kotlin
    @Query("SELECT * FROM custom_joke_table ORDER BY jokeId DESC")
    suspend fun getAllJokes(): List<Joke>
```

7. Add a `@Query` to `getLastJoke()`. Make the returned `Joke` nullable, so that it can handle if the table is empty:

```Kotlin
    //get the joke with the highest ID (last joke added)
    @Query("SELECT * FROM custom_joke_table ORDER BY jokeId DESC LIMIT 1")
    suspend fun getLastJoke(): Joke?
```

8. Add a `@Query` to get `numberOfJokes()`

```Kotlin
    //get the number of jokes present
    @Query("SELECT COUNT(*) FROM custom_joke_table")
    suspend fun numberOfJokes(): Int
```

## Create a JokeDatabase class

1. In `JokeDatabase.kt`, create an abstract class that extends `RoomDatabase`.

```Kotlin
@Database(entities = [Joke::class], version = 1, exportSchema = false)
abstract class JokeDatabase : RoomDatabase() {
```

2. Declare an abstract value that returns the `JokeDatabaseDao`:

```Kotlin
    abstract val jokeDatabaseDao: JokeDatabaseDao
```

3. Below, define a companion object:

```Kotlin
    companion object {
```

4. Inside the companion object, declare a private nullable variable `INSTANCE` for the database.

Initialize it to null, and annotate `INSTANCE` with `@Volatile`:
```Kotlin
        @Volatile
        private var INSTANCE: JokeDatabase? = null
```

5. Below, still inside the companion object, define the `getInstance()` method with a `Context` parameter, which will return a reference to the `SleepDatabase`:

```Kotlin
        fun getInstance(context: Context): JokeDatabase {}
```

6. Inside `getInstance()` add a `synchronized{}` block, and pass in `this`:
```Kotlin
            synchronized(this) {}
```

7. Inside the `synchronized` block, copy the current value of `INSTANCE` to a local variable, `instance`:

```Kotlin
                var instance = INSTANCE
```

8. At the end of the `synchronized` block, still inside the block, return `instance`:

```Kotlin
                return instance
```

9. Above the return statement, check if there is already a database stored in `instance`:

```Kotlin
                if (instance == null) {}
```

10. Invoke Room's `databaseBuilder` and supply the context that we passed in, the database class, and a name for the database:
```Kotlin
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JokeDatabase::class.java,
                        "custom_joke_database"
                    )
```

11. Add the required migration strategy to the builder:

```Kotlin
                        .fallbackToDestructiveMigration()
```

12. And call `.build()`:

```Kotlin
                        .build()
```

13. Assign `INSTANCE = instance` as the final step inside the `if` statement:

```Kotlin
                    INSTANCE = instance
```

14. Your final code should look like this:
```Kotlin
@Database(entities = [Joke::class], version = 1, exportSchema = false)
abstract class JokeDatabase : RoomDatabase() {

    abstract val jokeDatabaseDao: JokeDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: JokeDatabase? = null

        fun getInstance(context: Context): JokeDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        JokeDatabase::class.java,
                        "custom_joke_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
```

# Adding a ViewModel

## In AddJokeViewModel

1. Open `AddJokeViewModel.kt`
2. Inspect the provided `AddJokeViewModel` class definition.

Since the constructor needs parameters, we will need a factory.

```Kotlin
class AddJokeViewModel(
    val database: JokeDatabaseDao, 
    application: Application): AndroidViewModel(application) {
}
```

3. Create a `AddJokeViewModelFactory` class:

```Kotlin
class AddJokeViewModelFactory (
    private val dataSource: JokeDatabaseDao, 
    private val application: Application): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddJokeViewModel::class.java)) {
            return AddJokeViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

When `create` is called, it checks whether it can create this type. Then it creates the `AddJokeViewModel` using the previously stored `dataSource` and `application`.

4. Create the `AddJokeViewModelFactory` in `AddJokeFragment` in the `onCreateView` function:

```Kotlin
        val viewModelFactory = AddJokeViewModelFactory(dataSource, appContext)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddJokeViewModel::class.java)
```

## Test the app

1. Run the app and try to insert a joke: it fails!
2. Fix by making the `insert` and `update` functions `suspend`

```Kotlin
    @Insert
    suspend fun insert(joke: Joke)

    @Update
    suspend fun update(joke: Joke)
```

## Extra: run database calls in IO dispatcher

1. [Extra] the database calls are now suspendable, but they are still running in the main thread.

Add a `viewModelJob` to `JokeViewModel`:
```Kotlin
    private var viewModelJob = Job()
```

2. Override `onCleared` to cancel all pending coroutines:

```Kotlin
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
```

3. Now replace occurrences of `viewModelScope` with `uiScope`

Since `uiScope` is tied to the `viewModelJob`, this will implement the cancelation of the coroutines.

```Kotlin
    private val uiScope = CoroutineScope(Dispatchers.Main +  viewModelJob)
```

4. Make the database calls run in the IO thread:

```Kotlin
    //Suspend functions
    private suspend fun getNumberOfJokesFromDatabase(): Int{
        return withContext(Dispatchers.IO) {
            database.numberOfJokes()
        }
    }

    private suspend fun getAllJokes(): List<Joke>{
        return withContext(Dispatchers.IO) {
            database.getAllJokes()
        }
    }
```

5. Do the same for `AddJokeViewModel`:

Add a `viewModelJob`:
```Kotlin
    private var viewModelJob = Job()
```

6. Override `onCleared` to cancel all pending coroutines:

```Kotlin
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
```

7. Now replace occurrences of `viewModelScope` with `uiScope`

Since `uiScope` is tied to the `viewModelJob`, this will implement the cancelation of the coroutines.

8. Make the database calls run in the IO thread:

```Kotlin
    //suspend methods
    suspend fun saveJokeToDatabase(newJoke: Joke){
        return withContext(Dispatchers.IO){
            database.insert(newJoke)
        }
    }
```

# Button states and smiley event

1. Open `fragment_joke.xml`.
2. Add the `enabled` property to each button, and give it the value of a state variable:

```Kotlin
android:enabled="@{jokes.buttonVisible}"
```

3. Open `JokeViewModel.kt` and create a mapping:

```Kotlin
    val buttonVisible = Transformations.map(numberOfJokes){
        it > 0
    }
```

4. Create an encapsulated event:
```Kotlin
    private val _showSmileyEvent = MutableLiveData<Boolean>()
    val showSmileyEvent : LiveData<Boolean>
        get() = _showSmileyEvent

    fun showImageComplete(){
        _showSmileyEvent.value = false
    }
```

5. In `JokeFragment`, add an observer:
```Kotlin
        viewModel.showSmileyEvent.observe(viewLifecycleOwner) { })
```

6. Display the smiley, and immediately reset the event:
```Kotlin
            if (it) {
                showHappySmiley()
                viewModel.showImageComplete()
            }
```

7. To trigger the smiley, set the event value to `true` in the `goodJoke` function of `JokeViewModel`:
```Kotlin
        _showSmileyEvent.value = true
```
