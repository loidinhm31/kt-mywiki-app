package com.july.wikipedia.database.entities

import android.service.autofill.FillEventHistory
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.july.wikipedia.models.WikiPageDto
import com.july.wikipedia.models.WikiThumbnailDto

/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */

@Entity
data class Item(
    @PrimaryKey val id: Long,
    val title: String,
    val url: String,
    @Nullable val imageUrl: String?,
    val isFav: Boolean = false,
    val isSeen: Boolean = false,
    val updatedDate: String
)

/**
 * Map Item to domain model
 */
fun List<Item>.asDomainModel(): List<WikiPageDto> {
    return map {
        WikiPageDto(
            pageId = it.id,
            title = it.title,
            fullUrl = it.url,
            thumbnail = WikiThumbnailDto(it.imageUrl, null, null),
            isFav = it.isFav,
            isSeen = it.isSeen
        )
    }
}