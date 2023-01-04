package com.udacity.asteroidradar.database.remote

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.utils.Constants
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

// Since we only have one service, this can all go in one file.
// If you add more services, split this to multiple files and make sure to share the retrofit
// object between services.

/**
 * A retrofit service to fetch a asteroids list.
 */
interface AsteroidsApiService {
    @GET("neo/rest/v1/feed")
    fun getAsteroidsAsync(@Query("api_key") apiKEy:String,
                          @Query("start_date") startDate:String,
                          @Query("end_date") endDate:String): Deferred<Any>

    @GET("planetary/apod")
    fun getPictureOfDayAsync(@Query("api_key") apiKEy:String): Deferred<PictureOfDay>
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * client to set request timout
 * */
val client =  OkHttpClient().newBuilder().apply {
     this.connectTimeout(10, TimeUnit.SECONDS)
    this.readTimeout(30, TimeUnit.SECONDS)
}

/**
 * Main entry point for network access. Call like `Network.asteroidService.getAsteroids()`
 */
object Network {

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client.build())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val asteroidService = retrofit.create(AsteroidsApiService::class.java)
}