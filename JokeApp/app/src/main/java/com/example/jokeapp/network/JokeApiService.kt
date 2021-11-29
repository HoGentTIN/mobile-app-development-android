package com.example.jokeapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers

private const val BASE_URL = "https://dad-jokes.p.rapidapi.com/"


//create moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


//Scalars Converter = converter for strings to plain text bodies
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

/*private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()*/


interface JokeApiService{

    @Headers("x-rapidapi-key: 08f9dd6646msh683da6dc2cdc06fp1fee21jsn7fb6de1479aa")
    @GET("random/joke")
    fun getJokes(): Call<ApiJoke>

}

object JokeApi{
    //lazy properties = thread safe --> can only be initialized once at a time
    //adds extra safety to our 1 instance we need.
    val retrofitService : JokeApiService by lazy {
        retrofit.create(JokeApiService::class.java)
    }
}