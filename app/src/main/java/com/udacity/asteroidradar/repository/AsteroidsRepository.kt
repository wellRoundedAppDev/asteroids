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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.lang.Exception

class AsteroidsRepository(private val database: AsteroidDatabase) {

    val asteroids: LiveData<List<Asteroid>> = Transformations.map(database.asteroidDao.getAsteroids()){
        it.asDomainModel()
    }

    suspend fun refreshAsteroids(){
        withContext(Dispatchers.IO){
            try {
                var jsonString = AsteroidApi.retrofitService.getAsteroids(
                    Constants.API_KEY,)

                val asteroids = NetworkAsteroidContainer(parseAsteroidsJsonResult(JSONObject(jsonString)))
                database.asteroidDao.insertAll(*asteroids.asDatabaseModel())

            }catch (e: Exception){
                Log.i("error", e.message.toString())
            }
        }
    }

}