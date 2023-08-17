# Lesson 1

## Target
You'll setup your first application. You will learn about
- the app structure
- some basic UI elements
- how to position elements on the screen
- how to use the preview functionality
- how to add a theme
- how to access resources

## Setup the app
Start with the starter code (you can find it on the startercode branch).
The project is created by Android studio as a blank project with 1 activity and Android Compose.
Android studio Giraffe is used (attow 2023)

## Change the MainActivity structure
To add content to the app, modify the MainActivity.kt file. 
### change the default greeting to 'app' 
It is common to use an "app" composable as an entry point for your application. 
The app composable will later contain different composables that will represent the screens of the app. 

Adjust the preview accordingly. Make sure the surface is added as well. 

### add a task to the tasklist
Create a dataclass to represent a task. To start the task only contains text. 
```
data class Task (val name: String){
    companion object TaskSampler{
        val sampleTasks = listOf(
            "clean the oven",
            "put the garbage out",
            "buy 2l milk",
            "study for Android",
            "water the plants",
            "feed the cat",
            "feed the dog"
            )
        val getOne: () -> Task = {Task(sampleTasks[Random.nextInt(0, sampleTasks.size)])}
    }
}
```

Create a composable to display a task. 
Your code might look like this: 

```
@Composable
fun TaskApp() {
    Task(name = data.Task.getOne().name)
}

@Composable
fun Task(name: String = "", modifier: Modifier = Modifier) {
    Text(
        text = "$name",
        modifier = modifier
    )
}
```

Notice the Modifier, adding the default modifier like this, is a good practise. It allows you to control the appearance of the composable.

### Get familiar with some UI elements
Add a checkbox, some more text, a row and a column to the Composables. Structure them so your task looks like something you could find in an app.


## Access a resource for the background
Add an image resource through the resource manager. Choose "Import" and make sure the image doesn't scale automatically (no density). 

Put the image in a box to display it as a background.

## Well done! 
Congratulations, this is the end of the first contributions to the example app. Next week 