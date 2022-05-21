package com.july.wikipedia.managers

import com.july.wikipedia.models.WikiPage
import com.july.wikipedia.repositories.FavoritesRepository
import com.july.wikipedia.repositories.HistoryRepository

class WikiManager(
    private val favoritesRepository: FavoritesRepository,
    private val historyRepository: HistoryRepository
) {

    private var favoritesCache: ArrayList<WikiPage>? = null
    private var historyCache: ArrayList<WikiPage>? = null

    fun getHistory(): ArrayList<WikiPage>? {
        if (historyCache == null) {
            historyCache = historyRepository.getAllHistory()
        }
        return historyCache
    }

    fun getFavorites(): ArrayList<WikiPage>? {
//        if (favoritesCache == null) {
//            favoritesCache = favoritesRepository.getAllFavorites()
//        }
        return favoritesCache
    }

    fun addFavorite(page: WikiPage) {
        favoritesCache?.add(page)
        favoritesRepository.addFavorite(page)
    }

    fun removeFavorite(pageId: Int) {
//        favoritesRepository.removeFavoriteById(pageId)
//        favoritesCache = favoritesCache!!.filter { it.pageid != pageId } as ArrayList<WikiPage>
    }

    fun getIsFavorite(pageId: Int): Boolean {
        return favoritesRepository.isArticleFavorite(pageId)
    }

    fun addHistory(page: WikiPage) {
        historyCache?.add(page)
        historyRepository.addFavorite(page)
    }

    fun clearHistory() {
        historyCache?.clear()
        val allHistory = historyRepository.getAllHistory()
        allHistory?.forEach { historyRepository.removePageById(it.pageid!!) }
    }
}