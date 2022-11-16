package com.july.wikipedia.localstore.services.impl

import android.content.Context
import com.july.wikipedia.localstore.repositories.ArticleDatabaseOpenHelper
import com.july.wikipedia.localstore.repositories.HistoryRepository
import com.july.wikipedia.localstore.services.HistoryService
import com.july.wikipedia.models.WikiPage

class HistoryServiceImpl: HistoryService {
    private var historyRepository: HistoryRepository?= null

    constructor(dbHelper: ArticleDatabaseOpenHelper) {
        historyRepository = HistoryRepository(dbHelper)
    }

    override fun getHistory(): ArrayList<WikiPage>? {
        return historyRepository?.getAllHistory()
    }

    override fun addHistory(page: WikiPage) {
        historyRepository?.addFavorite(page)
    }

    override fun clearHistory() {
        val allHistory = historyRepository?.getAllHistory()
        allHistory!!.forEach { historyRepository?.removePageById(it.pageid!!) }
    }
}