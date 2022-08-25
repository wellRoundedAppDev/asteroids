package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class MainViewModel : ViewModel() {

    private val _astroids: MutableLiveData<List<Asteroid>> = MutableLiveData<List<Asteroid>>()

    val astroids: LiveData<List<Asteroid>>
        get() = _astroids


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


    init {
        getAsteroids()
        getPicOfDay()
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            try {
                var jsonString = AsteroidApi.retrofitService.getAsteroids(
                    Constants.API_KEY,)

                _astroids.value = parseAsteroidsJsonResult(JSONObject(jsonString))

            }catch (e: Exception){
                Log.i("error", e.message.toString())
            }
        }
    }

    private fun getPicOfDay() {
        viewModelScope.launch {
            try {
                _picOfDay.value = AsteroidApi.retrofitService.getPictureOfTheDay(Constants.API_KEY)
            }catch (e: Exception){

            }
        }
    }

}