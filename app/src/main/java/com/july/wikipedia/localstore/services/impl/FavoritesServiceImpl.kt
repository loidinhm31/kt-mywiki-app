package com.july.wikipedia.localstore.services.impl

import com.july.wikipedia.localstore.repositories.ArticleDatabaseOpenHelper
import com.july.wikipedia.localstore.repositories.FavoritesRepository
import com.july.wikipedia.localstore.services.FavoritesService
import com.july.wikipedia.models.WikiPage

class FavoritesServiceImpl: FavoritesService {
    private var favoritesRepository: FavoritesRepository? = null

    constructor(dbHelper: ArticleDatabaseOpenHelper) {
        favoritesRepository = FavoritesRepository(dbHelper)
    }


    override fun getFavorites(): ArrayList<WikiPage>? {
        return favoritesRepository?.getAllFavorites()!!
    }


    override fun addFavorite(page: WikiPage) {
        favoritesRepository?.addFavorite(page)
    }


    override fun removeFavorite(pageId: Int) {
        favoritesRepository?.removeFavoriteById(pageId)
    }


    override fun isFavorite(pageId: Int): Boolean {
        return favoritesRepository!!.isArticleFavorite(pageId)
    }
}