package com.udacity.asteroidradar.database.repo

import android.util.Log
import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.database.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.local.AsteroidDatabase
import com.udacity.asteroidradar.database.remote.Network
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AsteroidRepo(val database: AsteroidDatabase) {

    /**
     * A list of asteroids.
     */
    val asteroids: LiveData<List<Asteroid>> =database.asteroidDao.getAsteroids()

    val pictureOfDay: LiveData<PictureOfDay> =database.asteroidDao.getPictureOfDay()
    /**
     * Refresh the asteroids stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     * To actually load the asteroids for use, observe [asteroids]
     */
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            runBlocking {
                database.asteroidDao.deleteAllOldAsteroids(Constants.getCurrentDate())
            }
            val allAsteroids = Network.asteroidService.getAsteroidsAsync(BuildConfig.ASTEROID_API_KEY,
                Constants.getCurrentDate(),
                Constants.get7Date()).await()
            val pictureOfDay = Network.asteroidService.getPictureOfDayAsync(BuildConfig.ASTEROID_API_KEY).await()
            database.asteroidDao.insertPictureOfDay(pictureOfDay)
           insertAllAstroids(allAsteroids)
        }
    }

    private  fun insertAllAstroids(allAsteroids: Any) {
        var jsonObject= JSONObject(allAsteroids as Map<Any,Any>)
        database.asteroidDao.insertAllAsteroid(parseAsteroidsJsonResult(jsonObject))
    }
}