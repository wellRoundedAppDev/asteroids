package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDao{
    @Query("select * from database_asteroid  ORDER BY closeApproachDate")
    fun getAsteroids(): LiveData<List<DataBaseAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DataBaseAsteroid)
}

@Database(entities = [DataBaseAsteroid::class], version = 1)
abstract class AsteroidDatabase: RoomDatabase(){
    abstract val asteroidDao: AsteroidDao

}

private lateinit var _INSTANCE: AsteroidDatabase
fun getDataBase(context: Context): AsteroidDatabase{
    synchronized(AsteroidDatabase::class.java){
        if(!::_INSTANCE.isInitialized){
            _INSTANCE = Room.databaseBuilder(context.applicationContext,AsteroidDatabase::class.java,"asteroids").build()
        }
    }
    return _INSTANCE
}