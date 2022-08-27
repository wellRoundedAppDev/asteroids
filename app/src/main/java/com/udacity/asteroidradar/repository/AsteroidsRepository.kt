package com.udacity.asteroidradar.repository

import NetworkAsteroidContainer
import android.net.Network
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import asDatabaseModel

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.getTimeNowInStringFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class AsteroidsRepository(private val database: AsteroidDatabase) {

    var asteroids = Transformations.map(database.asteroidDao.getAllAsteroids()){
        it.asDomainModel()
    }



    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            try {
                var jsonString = AsteroidApi.retrofitService.getAllAsteroids(
                    Constants.API_KEY,)

                var allAsteroids = NetworkAsteroidContainer(parseAsteroidsJsonResult(JSONObject(jsonString)))
                database.asteroidDao.insertAll(*allAsteroids.asDatabaseModel())
                asteroids = Transformations.map(database.asteroidDao.getAllAsteroids()){
                    it.asDomainModel()
                }

            }catch (e: Exception){
                Log.i("error", e.message.toString())
            }
        }
    }

    suspend fun getDailyAsteroids(){

        withContext(Dispatchers.IO){
            try {
                var dailyAsteroids = database.asteroidDao.getDailyAsteroids(getTimeNowInStringFormat())
                asteroids = Transformations.map(dailyAsteroids){
                    it.asDomainModel()
                }

            }catch (e: Exception){
                Log.i("error", e.message.toString())
            }
        }
    }



}