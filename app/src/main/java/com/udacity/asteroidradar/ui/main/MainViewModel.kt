package com.udacity.asteroidradar.ui.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.local.getDatabase
import com.udacity.asteroidradar.database.repo.AsteroidRepo
import com.udacity.asteroidradar.domain.FilterAsteroids
import com.udacity.asteroidradar.ui.main.adapter.AsteroidClickListener
import com.udacity.asteroidradar.ui.main.adapter.AsteroidsAdapter
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.reflect.jvm.internal.impl.load.java.Constant


class MainViewModel(application: Application, val asteroidClickListener: AsteroidClickListener) :
    AndroidViewModel(application) {


    private val database = getDatabase(application)
    private val asteroidRepo = AsteroidRepo(database)
    var asteroidsAdapter = AsteroidsAdapter(asteroidClickListener)

    /**
     * init{} is called immediately when this ViewModel is created.
     */
    init {
        viewModelScope.launch {
            try {
                asteroidRepo.refreshAsteroids()
            }catch (e:HttpException){}
            catch (e:Exception){}

        }
    }

    var asteroids = asteroidRepo.asteroids
    val pictureOfDay = asteroidRepo.pictureOfDay

    fun filterAsteroids(filterAsteroids: FilterAsteroids) {
        asteroids = Transformations.map(asteroidRepo.asteroids) { asteroid ->
            when(filterAsteroids){
                FilterAsteroids.WEAK_ASTEROID->{
                    asteroid.filter { it.closeApproachDate>=Constants.getCurrentDate() }
                }
                FilterAsteroids.TODAY_ASTEROID->{
                    asteroid.filter { it.closeApproachDate.equals(Constants.getCurrentDate()) }
                }
                FilterAsteroids.SAVED_ASTEROID->{
                    asteroid
                }
            }

        }

    }

/**
 */

/**
 * Factory for constructing MainViewModel with parameter
 */
class Factory(val app: Application, val asteroidClickListener: AsteroidClickListener) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(app, asteroidClickListener) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}
}