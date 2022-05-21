package com.july.wikipedia.services

import com.july.wikipedia.models.WikiResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://en.wikipedia.org/"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface WikiApiService {

    @GET("w/api.php")
    suspend fun getRandomItems(@Query(value = "action") action : String,
                               @Query(value = "format") format : String,
                               @Query(value = "formatversion") formatVersion : Int,
                               @Query(value = "generator") generator: String,
                               @Query(value = "grnnamespace") grnNamespace : Int,
                               @Query(value = "prop") prop : String,
                               @Query(value = "grnlimit") grnLimit : Int,
                               @Query(value = "inprop") inProp : String,
                               @Query(value = "pithumbsize") thumbSize : Int) : WikiResult

    @GET("w/api.php")
    suspend fun searchItems(@Query(value = "action") action : String,
                            @Query(value = "formatversion") formatVersion : Int,
                            @Query(value = "format") format : String,
                            @Query(value = "generator") generator : String,
                            @Query(value = "gpssearch") gpsSearch: String,
                            @Query(value = "gpslimit") gpsLimit: Int,
                            @Query(value = "gpsoffset") gpsOffset: Int,
                            @Query(value = "prop") prop : String,
                            @Query(value = "piprop") piProp : String,
                            @Query(value = "pithumbsize") thumbSize : Int,
                            @Query(value = "pilimit") piLimit : Int,
                            @Query(value = "wbptterms") wbptTerms : String,
                            @Query(value = "inprop") inProp : String) : WikiResult

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object WikiApi {
    val retrofitService: WikiApiService by lazy { retrofit.create(WikiApiService::class.java) }
}