package com.july.wikipedia.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.july.wikipedia.database.entities.Item
import com.july.wikipedia.database.entities.asDomainModel
import com.july.wikipedia.database.rooms.ItemDatabase
import com.july.wikipedia.models.NetworkWikiPageContainer
import com.july.wikipedia.models.WikiPageDto
import com.july.wikipedia.models.asDatabaseModel
import com.july.wikipedia.services.WikiApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemRepository(private val itemDatabase: ItemDatabase) {
    // Transform item immediately
    val items: LiveData<List<WikiPageDto>> =
        Transformations.map(itemDatabase.itemDao.findItems(20)) {
            it.asDomainModel()
        }

    suspend fun saveRefreshItems(page: Int): List<WikiPageDto> {
        val pages: List<WikiPageDto>
        withContext(Dispatchers.IO) {
            // Retrieve items from API
            pages = WikiApi.retrofitService.getRandomItems(
                "query", "json", 2, "random",
                0, "pageimages|info", page, "url", 200
            ).query?.pages!!

            // Save all items into database
            itemDatabase.itemDao.insertAll(NetworkWikiPageContainer(pages).asDatabaseModel())
        }
        return pages
    }

    suspend fun updateFavorite(itemId: Long, isFav: Boolean) {
        withContext(Dispatchers.IO) {
            itemDatabase.itemDao.updateFavorite(itemId, isFav)
        }
    }

    suspend fun findFavorite(itemId: Long): Boolean {
        var isFav: Boolean
        withContext(Dispatchers.IO) {
            isFav = itemDatabase.itemDao.findFavoriteItem(itemId)
        }
        return isFav
    }

    suspend fun findItemByFavorite(isFav: Boolean): List<Item> {
        val items: List<Item>
        withContext(Dispatchers.IO) {
            items = itemDatabase.itemDao.findItemByFavorite(isFav)
        }
        return items
    }

    suspend fun updateHistory(itemId: Long, isSeen: Boolean) {
        withContext(Dispatchers.IO) {
            itemDatabase.itemDao.updateSeen(itemId, isSeen)
        }
    }

    suspend fun findItemByHistory(isSeen: Boolean): List<Item> {
        val items: List<Item>
        withContext(Dispatchers.IO) {
            items = itemDatabase.itemDao.findItemBySeen(isSeen)
        }
        return items
    }

    suspend fun deleteHistory(isSeen: Boolean) {
        withContext(Dispatchers.IO) {
            itemDatabase.itemDao.deleteItemBySeen(isSeen)
        }
    }
}