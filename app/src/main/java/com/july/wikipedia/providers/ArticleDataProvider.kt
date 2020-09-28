package com.july.wikipedia.providers

import android.util.Log
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson
import com.july.wikipedia.models.Urls
import com.july.wikipedia.models.WikiResult
import java.io.Reader
import java.lang.Exception
import java.util.*

class ArticleDataProvider {
    init {
        FuelManager.instance.baseHeaders = mapOf("User-Agent" to "Wikipedia Server")
    }

    fun search(term: String, skip: Int, take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getSearchUrl(term, skip, take).httpGet()
            .responseObject(WikipediaDataDeserializer()) { _, response, result ->

                if (response.statusCode != 200) {
                    throw Exception("Unable to get articles")
                }

                val(data, _) = result
                responseHandler.invoke(data as WikiResult)


            }
    }


    fun getRandom(take: Int, responseHandler: (result: WikiResult) -> Unit?) {
        Urls.getRandomUrl(take).httpGet()
            .responseObject(WikipediaDataDeserializer()) { _, response, result ->

                if (response.statusCode != 200) {
                    throw Exception("Unable to get articles")
                }

                val (data, _) = result
                responseHandler.invoke(data as WikiResult)

                Log.i("video", result.toString())

            }
    }

    class WikipediaDataDeserializer: ResponseDeserializable<WikiResult> {
        override fun deserialize(reader: Reader): WikiResult = Gson().fromJson(reader, WikiResult::class.java)

    }
}


