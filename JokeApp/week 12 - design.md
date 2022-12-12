# Floating action button (FAB)

[note: the material library has already been added]

Go to material.io -> Components -> Buttons -> FAB

Scroll down to Resources, and follow the link for Implementation to `MDC-Android`

Scroll down to the code, and use it as an example.

## Implementation
Add a coordinator layout around the constraint layout in `fragment_joke_overview.xml`.

Add the FAB as the last child of the new coordinator layout.

Since we don't have the icon, we can use a different one. Right-click on `res`, and select `New` -> `Vector Asset`.
Click on the `Clip Art` icon, and search for `add`. Add the icon, and update the xml.

# Theming

Go to the material.io website. Select Styles -> Color -> Overview. Scroll down to the Resources table. Select `M2 guideline - Color`. Use the menu on the right to scroll down to `Tools for picking colors`.

On the bottom right of the first image, there is a link to `View in color tool`, open the link.

Choose a primary color and a secondary color, and export the result.

Add the colors to `colors.xml`.

Now to use them, create a new theme `Theme.JokeApp.Day` with parent `Theme.Material3.Light`. Add the following mapping (source: lesson 10, exercise 15, part 5):

```xml
<item name="colorPrimary">@color/primaryColor</item>
<item name="colorPrimaryDark">@color/primaryDarkColor</item>
<item name="colorPrimaryVariant">@color/primaryLightColor</item>
<item name="colorOnPrimary">@color/primaryTextColor</item>
<item name="colorSecondary">@color/secondaryColor</item>
<item name="colorSecondaryVariant">@color/secondaryDarkColor</item>
<item name="colorOnSecondary">@color/secondaryTextColor</item>
```

Add `android:theme` to the android manifest, and point it to `@style/Theme.JokeAppTheme`. Run the app.

Create a new color scheme for dark mode, and download the xml. Create a new color file for dark mode. Add the colors to the file. Now duplicate the light theme to the dark theme xml, and change the parent to `Theme.Material3.Dark`.

# Add filtering using chips

Go to the Chips documentation. Scroll down to `ChipGroup`. Add the xml to the joke overview xml.

Create a new xml `filter_chip.xml` for the individual chips. Scroll to filter chip. Hint: you'll want the `Filter` style.

Add a call to `addChips` in `onCreateView` of `JokeOverviewFragment`:

```kotlin
        addChips(listOf("<10", "10-20", ">20"))
```

Create the function. Start with finding the `chipGroup` and the layout inflater:

```Kotlin
    val chipGroup = binding.chipGroup
    val inflator = LayoutInflater.from(chipGroup.context)
    chips.forEach {}
```

To create the chips, inside map, add:

```Kotlin
    val chip = inflator.inflate(R.layout.filter_chip, chipGroup, false) as Chip
    chip.text = it
    chip.tag = it
    chip.setOnCheckedChangeListener{
        button, isChecked ->
        viewModel.filterChip(button.tag as String, isChecked)
    }
```

Then add the children to the chipgroup:

```Kotlin
    chipGroup.addView(chip)
```

Now implement the `filterChip` function in the `viewModel`. We need a `LiveData` pointing to the filter string, so that we can update the resulting list:

```Kotlin
    private val _filter = MutableLiveData<String?>(null)
```

Let's implement the `filterChip` function:

```Kotlin
    fun filterChip(filter: String, checked: Boolean) {
        _filter.value = if (checked) tag else null
    }
```

Now, we'll use `Transformations.switchMap` to listen to changes from one `LiveData` and generate a new `LiveData`:

```Kotlin
    val jokes = Transformations.switchMap(_filter) {
        when (it) {
            "<10" -> database.getUnder10JokesLive()
            "10-20" -> database.getbetween1020JokesLive()
            ">20" -> database.getgreater20JokesLive()
            else -> database.getAllJokesLive()
        }
    }
```

Add the database functions:

```Kotlin
    @Query("SELECT * FROM custom_joke_table WHERE length(joke_punchline) < 10 ORDER BY jokeId DESC")
    abstract fun getUnder10JokesLive(): LiveData<List<DatabaseJoke>>

    @Query("SELECT * FROM custom_joke_table WHERE length(joke_punchline) BETWEEN 10 AND 20 ORDER BY jokeId DESC")
    abstract fun getbetween1020JokesLive(): LiveData<List<DatabaseJoke>>

    @Query("SELECT * FROM custom_joke_table WHERE length(joke_punchline) >= 20 ORDER BY jokeId DESC")
    abstract fun getgreater20JokesLive(): LiveData<List<DatabaseJoke>>
```

To verify that this really updates when the livedata updates, we can add some test code:

```Kotlin
private val _tstJokes = MutableLiveData<List<DatabaseJoke>>()

init {
    _tstJokes.value = "The quick brown fox jumps over the lazy dog".split(" ").map {
        DatabaseJoke(0, "", "", it)
    }
    viewModelScope.launch {
        while (true) {
            val prevValues = _tstJokes.value!!
            _tstJokes.value = prevValues.subList(1, prevValues.size) + prevValues.subList(0, 1)
            delay(500)
        }
    }
}
```

Add a key to the switchMap and the createChips call to test.

Notice the way the labels are updated. This is because our ids are all 0. Let's fix that:

```Kotlin
var jokeIdCounter = 0L
_tstJokes.value = "The quick brown fox jumps over the lazy dog".split(" ").map {
    DatabaseJoke(jokeIdCounter++, "", "", it)
}
```

Run the code again, and observe the difference.
