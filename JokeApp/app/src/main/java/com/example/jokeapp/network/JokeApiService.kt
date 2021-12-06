package com.example.jokeapp.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "https://dad-jokes.p.rapidapi.com/"


//create moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val logger = HttpLoggingInterceptor().apply{level = HttpLoggingInterceptor.Level.BASIC}

private val client = OkHttpClient.Builder()
    .addInterceptor(logger)
    .build()
//Scalars Converter = converter for strings to plain text bodies
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

/*private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()*/


interface JokeApiService{

    @Headers("x-rapidapi-key: 08f9dd6646msh683da6dc2cdc06fp1fee21jsn7fb6de1479aa")
    @GET("random/joke")
    fun getJokes(): Deferred<ApiJokeContainer>

    /*
    * Note: the joke api doesn't have a POST endpoint
    * This is just an example
    * */
    @POST("joke")
    fun putJoke(@Body joke: ApiJoke): Deferred<ApiJoke>

}

object JokeApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : JokeApiService by lazy {
        retrofit.create(JokeApiService::class.java)
    }

    fun JokeApiService.mockPutJoke(joke: ApiJoke):ApiJoke{
        return joke
    }
}