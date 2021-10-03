package com.july.wikipedia.fragments

import com.july.wikipedia.WikiApplication
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.july.wikipedia.R
import com.july.wikipedia.adapters.ArticleListItemRecyclerAdapter
import com.july.wikipedia.localstore.services.HistoryService
import com.july.wikipedia.managers.WikiManager
import com.july.wikipedia.models.WikiPage
import org.jetbrains.anko.alert
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton


class HistoryFragment : Fragment() {
    private var wikiManager: WikiManager? = null

    private var historyRecycler: RecyclerView? = null
    private var adapter: ArticleListItemRecyclerAdapter = ArticleListItemRecyclerAdapter()

    init {
        // tell parent activity of the fragment, we are going to use this fragment
        // ...and show menu option of this fragment
        setHasOptionsMenu(true)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_history, container, false)


        historyRecycler = view.findViewById(R.id.history_article_recycler)
        historyRecycler!!.layoutManager = LinearLayoutManager(context)
        historyRecycler!!.adapter = adapter

        return view
    }

    override fun onResume() {
        super.onResume()

        doAsync {
            val historyArticles = wikiManager!!.getHistory()
            adapter.currentResults.clear()
            adapter.currentResults.addAll(historyArticles as ArrayList<WikiPage>)

            activity?.runOnUiThread { adapter.notifyDataSetChanged() }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_clear_history) {
            // show confirmation alert
            activity?.alert("Are you want to clear your history?", "Confirm") {
                yesButton {
                    // yes was hit...
                    // clear history async
                    adapter.currentResults.clear()

                    doAsync {
                        wikiManager?.clearHistory()
                    }

                    activity?.runOnUiThread { adapter.notifyDataSetChanged() }
                }
                noButton {
                    // let it blank
                }
            }?.show()
        }

        return super.onOptionsItemSelected(item)
    }

}