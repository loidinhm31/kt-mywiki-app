package com.july.wikipedia.managers

import com.july.wikipedia.localstore.repositories.FavoritesRepository
import com.july.wikipedia.localstore.repositories.HistoryRepository
import com.july.wikipedia.localstore.services.FavoritesService
import com.july.wikipedia.localstore.services.HistoryService
import com.july.wikipedia.localstore.services.impl.FavoritesServiceImpl
import com.july.wikipedia.models.WikiPage

class WikiManager(private val favoritesService: FavoritesService,
                    private val historyService: HistoryService) {

    private var favoritesCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null



    fun getHistory(): ArrayList<WikiPage>? {
        if (historyCache == null) {
            historyCache = historyService.getHistory()
        }
        return historyCache
    }

    fun getFavorites(): ArrayList<WikiPage>? {
        if (favoritesCache == null) {
            favoritesCache = favoritesService.getFavorites()
        }
        return favoritesCache
    }

    fun addFavorite(page: WikiPage) {
        favoritesCache?.add(page)
        favoritesService.addFavorite(page)
    }

    fun removeFavorite(pageId: Int) {
        favoritesService.removeFavorite(pageId)
        favoritesCache = favoritesCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavorite(pageId: Int): Boolean {
        return favoritesService.isFavorite(pageId)
    }

    fun addHistory(page: WikiPage) {
        historyCache?.add(page)
        historyService.addHistory(page)
    }

    fun clearHistory() {
        historyCache?.clear()
        historyService.clearHistory()
    }
}