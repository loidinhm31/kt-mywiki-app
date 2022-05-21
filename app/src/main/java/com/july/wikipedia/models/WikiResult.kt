package com.july.wikipedia.models

import com.squareup.moshi.Json

data class WikiResult(
    @Json(name = "query") val query: WikiQueryData?
)