package com.july.wikipedia.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.july.wikipedia.R
import com.july.wikipedia.WikiApplication
import com.july.wikipedia.activities.SearchActivity
import com.july.wikipedia.adapters.ArticleCardRecyclerAdapter
import com.july.wikipedia.managers.NetworkManager
import com.july.wikipedia.managers.WikiManager

import java.lang.Exception


class ExploreFragment : Fragment() {
    //private val articleProvider: ArticleDataProvider = ArticleDataProvider()

    private var wikiManager: WikiManager? = null

    var searchCardView: CardView? = null
    var exploreRecycler: RecyclerView? = null
    var refresher: SwipeRefreshLayout? = null
    var adapter: ArticleCardRecyclerAdapter = ArticleCardRecyclerAdapter()


    override fun onAttach(context: Context) {
        super.onAttach(context)

        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_explore, container, false)

        searchCardView = view.findViewById<CardView>(R.id.search_card_view)
        exploreRecycler = view.findViewById<RecyclerView>(R.id.explore_article_recycler)

        refresher = view.findViewById<SwipeRefreshLayout>(R.id.refresher)

        searchCardView!!.setOnClickListener{
            val searchIntent = Intent(context, SearchActivity::class.java)
            context?.startActivity(searchIntent)
        }

        exploreRecycler!!.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        exploreRecycler!!.adapter = adapter

        refresher?.setOnRefreshListener {
            getRandomArticle()
        }

        getRandomArticle()

        return view
    }

    private fun getRandomArticle() {
        refresher?.isRefreshing = true

        try {
            val networkManager = NetworkManager()
            if (networkManager.isNetworkAvailable(context!!)) {

                wikiManager?.getRandom(15) { wikiResult ->
                    adapter.currentResults.clear()
                    adapter.currentResults.addAll(wikiResult.query!!.pages)
                    activity!!.runOnUiThread { adapter.notifyDataSetChanged() }
                }


            } else {
                val toast: Toast = Toast.makeText(context, "Couldn't refresh explore", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }

            refresher?.isRefreshing = false

        } catch (e: Exception) {
            // show alert
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(e.message).setTitle("ERROR!").show()
        }
    }
}