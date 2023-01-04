package com.udacity.asteroidradar.database.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay

@Dao
interface AsteroidDao {

    @Query("select * from asteroid order by closeApproachDate")
    fun getAsteroids(): LiveData<List<Asteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllAsteroid(asteroids: ArrayList<Asteroid>)

    @Query("delete from asteroid where closeApproachDate<:currentDay ")
    fun deleteAllOldAsteroids(currentDay:String)

    @Query("select * from pictureofday")
    fun getPictureOfDay(): LiveData<PictureOfDay>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPictureOfDay(vararg pictureOfDay: PictureOfDay)

    @Query("delete from PictureOfDay")
    fun deletePictureOfDay()
}