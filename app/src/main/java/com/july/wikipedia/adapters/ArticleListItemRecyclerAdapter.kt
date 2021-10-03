package com.july.wikipedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.july.wikipedia.R
import com.july.wikipedia.holders.CardHolder
import com.july.wikipedia.holders.ListItemHolder
import com.july.wikipedia.models.WikiPage
import com.july.wikipedia.models.WikiResult

class ArticleListItemRecyclerAdapter : RecyclerView.Adapter<ListItemHolder>() {
    var currentResults: ArrayList<WikiPage> = ArrayList<WikiPage>()
    override fun getItemCount(): Int {
        return currentResults.size
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        // update view here
        var page = currentResults[position]
        holder.updateWithPage(page)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        var cardItem = LayoutInflater.from(parent.context).inflate(R.layout.article_list_item, parent, false)
        return ListItemHolder(cardItem)
    }
}