package com.july.wikipedia.models

import android.annotation.SuppressLint
import com.july.wikipedia.database.entities.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class NetworkWikiPageContainer(val pages: List<WikiPageDto>)

@JsonClass(generateAdapter = true)
data class WikiPageDto(
    @Json(name = "pageid") var pageId: Long,

    var title: String?,

    @Json(name = "fullurl") var fullUrl: String?,

    var thumbnail: WikiThumbnailDto?,

    var isFav: Boolean = false,

    var isSeen: Boolean = false,
)

/**
 * Convert Network results to database objects
 */
@SuppressLint("SimpleDateFormat")
fun NetworkWikiPageContainer.asDatabaseModel(): List<Item> {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val item = pages.map {
        Item(
            id = it.pageId,
            title = it.title!!,
            url = it.fullUrl!!,
            imageUrl = it.thumbnail?.source,
            isFav = it.isFav,
            updatedDate = sdf.format(Date()))
    }
    return item
}