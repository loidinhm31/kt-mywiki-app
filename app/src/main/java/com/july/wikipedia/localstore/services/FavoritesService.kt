package com.july.wikipedia.localstore.services

import com.july.wikipedia.models.WikiPage

interface FavoritesService {
    fun getFavorites(): ArrayList<WikiPage>?

    fun addFavorite(page: WikiPage)

    fun removeFavorite(pageId: Int)

    fun isFavorite(pageId: Int): Boolean
}