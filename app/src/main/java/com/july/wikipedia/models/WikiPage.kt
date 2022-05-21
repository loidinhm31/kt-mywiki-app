package com.july.wikipedia.models

data class WikiPage(
    var pageid: Int?,
    var title: String?,
    var fullurl: String?,
    var thumbnail: WikiThumbnail?
)