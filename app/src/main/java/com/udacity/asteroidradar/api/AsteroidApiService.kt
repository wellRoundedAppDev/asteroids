package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.*

private val _moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val _retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(_moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

interface AsteroidApiService{



    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
                             @Query("api_key") api_key: String,
                             ): String

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") api_key: String): PictureOfDay
}

object AsteroidApi{
    val retrofitService : AsteroidApiService by lazy {
        _retrofit.create(AsteroidApiService::class.java)
    }
}

