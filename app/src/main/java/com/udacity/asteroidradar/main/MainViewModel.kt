package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.getDataBase
import com.udacity.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

//    private val _astroids: MutableLiveData<List<Asteroid>> = MutableLiveData<List<Asteroid>>()
//
//    val astroids: LiveData<List<Asteroid>>
//        get() = _astroids


    private val _picOfDay: MutableLiveData<PictureOfDay> = MutableLiveData<PictureOfDay>()

    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    fun displayAsteroidDetails(asteroid: Asteroid){
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete(){
        _navigateToSelectedAsteroid.value = null
    }


    private val database = getDataBase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        getPicOfDay()
        getAsteroids()
    }



    private fun getAsteroids() {

        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }


//        viewModelScope.launch {
//            try {
//                var jsonString = AsteroidApi.retrofitService.getAsteroids(
//                    Constants.API_KEY,)
//
//                _astroids.value = parseAsteroidsJsonResult(JSONObject(jsonString))
//
//            }catch (e: Exception){
//                Log.i("error", e.message.toString())
//            }
//        }
  }
    val astroids = asteroidsRepository.asteroids


    private fun getPicOfDay() {
        viewModelScope.launch {
            try {
                _picOfDay.value = AsteroidApi.retrofitService.getPictureOfTheDay(Constants.API_KEY)
            }catch (e: Exception){

            }
        }
    }

}