package com.july.wikipedia.providers.services

import android.util.Log
import com.google.gson.*
import com.july.wikipedia.models.Urls
import com.july.wikipedia.models.WikiResult
import com.july.wikipedia.providers.ApiDataProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException
import java.lang.reflect.Type

class DataService {
    private val TAG = this::class.simpleName

    private var dataProvider: ApiDataProvider? = null


    constructor() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        dataProvider = retrofit.create(ApiDataProvider::class.java)
    }

    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        val call: Call<WikiResult> = dataProvider!!.getRandom(
            "query", "json", 2, "random", 0, "pageimages|info", take,
            "url", 200
        )

        call.enqueue(object : Callback<WikiResult> {
            override fun onResponse(call: Call<WikiResult>, response: Response<WikiResult>) {

                if (response.code() == 200) {
                    val data = response.body() as WikiResult

                    responseHandler.invoke(data)
                }
            }

            override fun onFailure(call: Call<WikiResult>, t: Throwable) {
                Log.e(TAG, "call failure")
                t.printStackTrace()
            }
        })
    }

    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        val call: Call<WikiResult> = dataProvider?.search(
            "query", 2, "prefixsearch", term, take, skip,"pageimages|info",
            "thumbnail|url", 200, take, "description", "json", "url"
        )!!

        call.enqueue(object : Callback<WikiResult> {
            override fun onResponse(call: Call<WikiResult>, response: Response<WikiResult>) {
                if (response.code() == 200) {
                    val data = response.body()

                    responseHandler.invoke(data as WikiResult)
                }
            }

            override fun onFailure(call: Call<WikiResult>, t: Throwable) {
                Log.e(TAG, "call failure")
                t.printStackTrace()
            }
        })
    }
}