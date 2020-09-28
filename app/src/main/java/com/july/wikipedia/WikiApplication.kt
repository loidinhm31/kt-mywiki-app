package com.july.wikipedia

import android.app.Application
import com.july.wikipedia.managers.WikiManager
import com.july.wikipedia.providers.ArticleDataProvider
import com.july.wikipedia.repositories.ArticleDatabaseOpenHelper
import com.july.wikipedia.repositories.FavoritesRepository
import com.july.wikipedia.repositories.HistoryRepository

class WikiApplication: Application() {
    private var dbHelper: ArticleDatabaseOpenHelper? = null
    private var favoritesRepository: FavoritesRepository? = null
    private var historyRepository: HistoryRepository? = null
    private var wikiProvider: ArticleDataProvider? = null
    var wikiManager: WikiManager? = null
        private set
    override fun onCreate() {
        super.onCreate()

        dbHelper = ArticleDatabaseOpenHelper(applicationContext)
        favoritesRepository = FavoritesRepository(dbHelper!!)
        historyRepository = HistoryRepository(dbHelper!!)
        wikiProvider = ArticleDataProvider()
        wikiManager = WikiManager(wikiProvider!!, favoritesRepository!!, historyRepository!!)

    }
}