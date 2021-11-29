package com.example.jokeapp.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://dad-jokes.p.rapidapi.com/"
//private const val BASE_URL = "https://mars.udacity.com/"

//Scalars Converter = converter for strings to plain text bodies
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface JokeApiService{

    @Headers("x-rapidapi-key: 08f9dd6646msh683da6dc2cdc06fp1fee21jsn7fb6de1479aa")
    @GET("random/joke")
    fun getJokes(): Call<String>
    /*@GET("realestate")
    fun getJokes(): Call<String>*/
}

object JokeApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : JokeApiService by lazy {
        retrofit.create(JokeApiService::class.java)
    }
}