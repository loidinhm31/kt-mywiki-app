package com.july.wikipedia.ui.adapters.binding

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.july.wikipedia.R
import com.july.wikipedia.models.WikiPageDto
import com.july.wikipedia.ui.adapters.FavoriteItemAdapter
import com.july.wikipedia.ui.adapters.HistoryItemAdapter

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("favoriteListData")
fun bindFavoriteItemsRecyclerView(recyclerView: RecyclerView, data: List<WikiPageDto>?) {
    val adapter = recyclerView.adapter as FavoriteItemAdapter
    adapter.submitList(data)
}

/**
 * Updates the data shown in the [RecyclerView].
 */
@BindingAdapter("historyListData")
fun bindHistoryItemsRecyclerView(recyclerView: RecyclerView, data: List<WikiPageDto>?) {
    val adapter = recyclerView.adapter as HistoryItemAdapter
    adapter.submitList(data)
}