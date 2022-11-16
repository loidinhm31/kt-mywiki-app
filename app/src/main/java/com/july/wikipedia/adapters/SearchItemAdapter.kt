package com.july.wikipedia.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.july.wikipedia.activities.ArticleDetailActivity
import com.july.wikipedia.databinding.SearchItemBinding
import com.july.wikipedia.models.WikiPage


class SearchItemAdapter :
    ListAdapter<WikiPage, SearchItemAdapter.SearchItemViewHolder>(DiffCallback) {

    /**
     * The MarsPhotosViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [WikiPage] information.
     */
    class SearchItemViewHolder(
        private var binding: SearchItemBinding // same pattern name with layout
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val detailPageIntent = Intent(itemView.context, ArticleDetailActivity::class.java)
                val pageJson = Gson().toJson(binding.resultPage)

                detailPageIntent.putExtra("page", pageJson)
                itemView.context.startActivity(detailPageIntent)
            }
        }

        fun bind(wikiPage: WikiPage) {
            binding.resultPage = wikiPage
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of
     * [WikiPage] has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<WikiPage>() {
        override fun areItemsTheSame(oldItem: WikiPage, newItem: WikiPage): Boolean {
            return oldItem.pageid == newItem.pageid
        }

        override fun areContentsTheSame(oldItem: WikiPage, newItem: WikiPage): Boolean {
            return oldItem.title == newItem.title
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchItemViewHolder {
        return SearchItemViewHolder(
            SearchItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val wikiPage = getItem(position)
        holder.bind(wikiPage)
    }

}