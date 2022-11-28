# Retrofit

## Introduction: exploring the API

Check out https://rapidapi.com/KegenGuyll/api/dad-jokes and https://dadjokes.io/documentation/endpoints/random-jokes

The `GET Random Jokes` response is:

`GET https://dad-jokes.p.rapidapi.com/random/joke`

```json
{
  "body": [
    {
      "_id": "5f80ccd641785ba7c7d27b99",
      "punchline": "Join the club.",
      "setup": "Bad at golf?",
      "type": "general"
    }
  ],
  "success": true
}
```

Note: you need to create a free account at rapidapi to call this API.

Example Kotlin code is available:

```Kotlin
val client = OkHttpClient()

val request = Request.Builder()
	.url("https://dad-jokes.p.rapidapi.com/random/joke")
	.get()
	.addHeader("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
	.addHeader("X-RapidAPI-Host", "dad-jokes.p.rapidapi.com")
	.build()

val response = client.newCall(request).execute()
```

## Coding it up

We need three ingredients:
- a class representing the data we want to fetch
- an interface declaring the API functions we want to call
- a service that implements this api

### The data

Look at the JSON from before, and map it to a class.

Since the body is an array, we can map it to a `List`:

```Kotlin
data class ApiJoke (
    val body: List<Joke>
)
```

Now we need to tell `Retrofit` which `JSON` fields map to which attributes. Use the `@Json(name = "...")` annotation on the `Joke` class fields.

### The API interface

Start with adding the dependencies:

```gradle
    // Moshi
    implementation "com.squareup.moshi:moshi:1.13.0"
    implementation "com.squareup.moshi:moshi-kotlin:1.13.0"

    // retrofit
    implementation "com.squareup.retrofit2:retrofit:2.9.0"
    implementation "com.squareup.retrofit2:converter-scalars:2.9.0"

        //Retrofit with coroutines
    implementation "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"

        // Retrofit with Moshi Converter
    implementation "com.squareup.retrofit2:converter-moshi:2.9.0"

    //logging for retrofit with OkHttp
    implementation "com.squareup.okhttp3:logging-interceptor:4.10.0"
```

Create an interface representing the API:

```Kotlin
interface JokeApiService{

    @Headers("x-rapidapi-key: [your API key]")
    @GET("random/joke")
    fun getJokes(): Deferred<ApiJoke>

}
```

### Create the service

Create a companion object within the service:

```Kotlin
    companion object {
        private const val BASE_URL = "https://dad-jokes.p.rapidapi.com/"

        val INSTANCE: JokeApiService by lazy {
            createRetrofitService()
        }

        private fun createRetrofitService(): JokeApiService {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(JokeApiService::class.java)
        }
    }
```

## Add a Fragment for the API joke

- Create a `screens.apiJoke` package
- Create a `ApiJokeFragment`
- Create a `ApiJokeViewModel`
- Add the viewmodel as a binding to the fragment
- Add a `TextView` for the joke setup
- Add a `TextView` for the joke punchline
- Add a variable for the viewmodel
- set the binding for both textfields to the viewmodel joke object
- Add the fragment to the navigation graph
- Add a menu item to the drawer menu using the (new) id in the navigation graph