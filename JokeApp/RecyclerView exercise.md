# Recyclerview

Create a function `getAllJokesLive` to fetch the Jokes as `LiveData`:

```Kotlin
    @Query("SELECT * FROM custom_joke_table ORDER BY jokeId DESC")
    fun getAllJokesLive(): LiveData<List<Joke>>
```

Note that this function is not `suspend`!

Room will update this `LiveData` list for us from its own background thread, and keep this list up to date.

Create a new package: `screens.jokeOverview`.

Create a new `Fragment (with ViewModel)` `JokeOverviewFragment`.

## The UI stuff

Convert the `FrameLayout` in the new fragment to a `ConstraintLayout`.

Remove the `TextView`, and add a `RecyclerView` from the `Containers` list (use the `Design` tab).

Convert to a data binding layout: open the `Code` tab, put the cursor on `ConstraintLayout` and use `[Alt-Enter]` to open up the menu.

Sync Project with Gradle Files (the elephant button) to generate the binding class.

Add a `binding` attribute in the `JokeOverviewFragment`:

```Kotlin
private lateinit var binding: FragmentJokeOverviewBinding
```

Create a `joke_row_item` layout, and add a TextView. This will represent our row item. Convert to a databinding layout. Add a binding for joke.

Result:

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
            name="joke"
            type="com.example.jokeapp.database.jokes.Joke" />
    </data>

    <TextView
        android:id="@+id/textView3"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingStart="16dp"
        android:paddingEnd="16dp"
        tools:text="Joke row item" />
    
</layout>
```

## The ViewModel

Add a constructor parameter for the Dao:

```Kotlin
class JokeOverviewViewModel(val database: JokeDatabaseDao):ViewModel()
```

Get the list of jokes as `LiveData` and store it in an attribute:

```Kotlin
val jokes = database.getAllJokesLive()
```

Now we need a `Factory` to create the `ViewModel`. Add an inner class called `Factory`, and make it implement `ViewModelProvider.Factory`.

Override the `create` function, and use it to return a new `JokeOverviewViewModel` instance. Your code should look like this:

```Kotlin
class Factory(private val dataSource: JokeDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JokeOverviewViewModel::class.java)) {
            return JokeOverviewViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

Now use the factory and a delegate to create the viewmodel in `JokeOverviewFragment`.

```Kotlin
    private val viewModel: JokeOverviewViewModel by viewModels() {
        val appContext = requireNotNull(this.activity).application
        val dataSource = JokeDatabase.getInstance(appContext).jokeDatabaseDao
        JokeOverviewViewModel.Factory(dataSource)
    }
```

To make the `by viewModels` syntax work, we need a new library: fragment-ktx. See https://developer.android.com/kotlin/ktx#fragment.

Check out other interesting Kotlin extensions on that page!

Add the dependencies to the app gradle file:

```gradle
implementation "androidx.fragment:fragment-ktx:1.5.4"
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.5.1"
```

Add a variable `viewModel` to `fragment_joke_overview.xml`:

```xml
<variable
    name="viewModel"
    type="com.example.jokeapp.screens.jokeOverview.JokeOverviewViewModel" />
```

## RecyclerView

Open up the fragment xml, and add `tools:listitem="@layout/joke_row_item"` to the `RecyclerView`. This way our row item is displayed correctly in the design tab.

Also add an id and set the layout manager. The result could look like this:

```xml
        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/joke_list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
            tools:listitem="@layout/joke_row_item"/>
```

Go back to `JokeOverviewFragment` and inflate the layout:

```Kotlin
binding = FragmentJokeOverviewBinding.inflate(inflater)
```

Check out the documentation on RecyclerView: https://developer.android.com/develop/ui/views/layout/recyclerview

Look up the documentation for `RecyclerView.Adapter`: https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView.Adapter

Notice:
- `ListAdapter`
- `PagedDataAdapter`

We'll use `ListAdapter`. There is a good codelab for paging: search for "android paging codelab".

The `UserAdapter` on the `ListAdapter` documentation is written in `Java`. Let's improvise.

We know we want to work with a list of `Joke`s. We need a custom `ViewHolder`. Thus our Adapter should look like this:

```Kotlin
class JokeOverviewAdapter : ListAdapter<Joke, JokeViewHolder>(JokeDiffUtil())
```

We need a diff util class. Derive it from `DiffUtil.ItemCallback<Joke>`:

```Kotlin
class JokeDiffUtil : DiffUtil.ItemCallback<Joke>()
```

Implement the required methods.

We also need a `JokeViewHolder` class, derived from `RecyclerView.ViewHolder`:

```Kotlin
class JokeViewHolder() : RecyclerView.ViewHolder()
```

Now complete the `JokeOverviewAdapter` by calling functions on `JokeViewHolder`:

```Kotlin
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return JokeViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
```

Add the `from` function inside a `companion object` in the `JokeViewHolder`:

```Kotlin
    companion object {
        fun from(parent: ViewGroup): JokeViewHolder {
            return JokeViewHolder(JokeRowItemBinding.inflate(LayoutInflater.from(parent.context)))
        }
    }
```

We're calling the `JokeViewHolder` constructor with the binding object. This is great! Update the constructor and add the `val` keyword to keep a reference to it.
Also add `binding.root` as the parameter to the `RecyclerView.ViewHolder` constructor.

Add the `bind` function to your `JokeViewHolder`:

```Kotlin
    fun bind(item: Joke?) {
        binding.joke = item
    }
```

Since the `joke` object is now bound, we can create a `@BindingAdapter`:

```Kotlin
@BindingAdapter("jokeText")
fun TextView.setJokeText(item: Joke) {
    text = item.punchline
}
```

And add it to the `joke_row_item.xml`:

```
        app:jokeText="@{joke}"
```

Now open up the `JokeOverviewFragment` and fix the adapter code:

```Kotlin
val adapter = JokeOverviewAdapter()
binding.jokeList.adapter = adapter

viewModel.jokes.observe(viewLifecycleOwner) {
    adapter.submitList(it)
}
```

## Add navigation to the new fragment

Open the navigation graph, and add the new fragment. Copy the id.

Now open up the `navdrawer_menu.xml`, and add a menu item for the new fragment:

```xml
    <item
        android:id="@+id/jokeOverviewFragment"
        android:title="Joke Overview" />
```

Now run the app!

