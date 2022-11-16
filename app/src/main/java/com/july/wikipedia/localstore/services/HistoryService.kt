package com.july.wikipedia.localstore.services

import com.july.wikipedia.models.WikiPage

interface HistoryService {
    fun getHistory(): ArrayList<WikiPage>?

    fun addHistory(page: WikiPage)

    fun clearHistory()

}