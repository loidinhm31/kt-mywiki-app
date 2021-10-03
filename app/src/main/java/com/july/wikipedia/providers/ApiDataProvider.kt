package com.july.wikipedia.providers

import com.july.wikipedia.models.WikiResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDataProvider {

    @GET("api.php")
    fun getRandom(
        @Query("action") action: String? = "query",
        @Query("format") format: String? = "json",
        @Query("formatversion") version: Int? = 2,
        @Query("generator") generator: String? = "random",
        @Query("grnnamespace") nameSpace: Int? = 0,
        @Query("prop") property: String? = "pageimages|info",
        @Query("grnlimit") take: Int,
        @Query("inprop") inProperty: String? = "url",
        @Query("pithumbsize") thumbSize: Int? = 200
    ) : Call<WikiResult>

    @GET("api.php")
    fun search(
        @Query("action") action: String? = "query",
        @Query("formatversion") version: Int? = 2,
        @Query("generator") generator: String? = "prefixsearch",
        @Query("gpssearch") term: String,
        @Query("gpslimit") take: Int,
        @Query("gpsoffset") skip: Int,
        @Query("prop") property: String? = "pageimages|info",
        @Query("piprop") piProperty: String? = "thumbnail|url",
        @Query("pithumbsize") thumbSize: Int? = 200,
        @Query("pilimit") limit: Int,
        @Query("wbptterms") wbptTerms: String? = "description",
        @Query("format") format: String? = "json",
        @Query("inprop") inProperty: String? = "url"
    ) : Call<WikiResult>
}