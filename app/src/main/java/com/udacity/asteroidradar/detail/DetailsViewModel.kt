package com.udacity.asteroidradar.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid

class DetailsViewModel(asteroid: Asteroid, app: Application): ViewModel() {


    private val _selectedAsteroid: MutableLiveData<Asteroid> = MutableLiveData<Asteroid>()
    val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid

    init {
        _selectedAsteroid.value = asteroid
    }




}