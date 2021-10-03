package com.july.wikipedia

import android.app.Application
import com.july.wikipedia.localstore.repositories.ArticleDatabaseOpenHelper
import com.july.wikipedia.localstore.services.FavoritesService
import com.july.wikipedia.localstore.services.HistoryService
import com.july.wikipedia.localstore.services.impl.FavoritesServiceImpl
import com.july.wikipedia.localstore.services.impl.HistoryServiceImpl
import com.july.wikipedia.managers.WikiManager

class WikiApplication: Application() {
    private var dbHelper: ArticleDatabaseOpenHelper? = null
    private var favoritesService: FavoritesService? = null
    private var historyService: HistoryService? = null

    var wikiManager: WikiManager? = null
        private set
    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesService = FavoritesServiceImpl(dbHelper!!)
        historyService = HistoryServiceImpl(dbHelper!!)

        wikiManager = WikiManager(favoritesService!!, historyService!!)
    }
}