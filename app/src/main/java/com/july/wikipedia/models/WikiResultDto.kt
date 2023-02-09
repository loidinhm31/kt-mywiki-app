package com.july.wikipedia.models

import com.squareup.moshi.Json

data class WikiResultDto(
    @Json(name = "query") val query: WikiQueryDataDto?
)