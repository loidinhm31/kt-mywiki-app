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
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.july.wikipedia.WikiApplication
import com.july.wikipedia.activities.SearchActivity
import com.july.wikipedia.adapters.WikiItemAdapter
import com.july.wikipedia.databinding.FragmentExploreBinding
import com.july.wikipedia.managers.NetworkManager
import com.july.wikipedia.managers.WikiManager
import com.july.wikipedia.viewmodels.OverviewViewModel

import java.lang.Exception


class ExploreFragment : Fragment() {
    private val viewModel: OverviewViewModel by viewModels()

    private var wikiManager: WikiManager? = null

    private var searchCardView: CardView? = null

    var refresher: SwipeRefreshLayout? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        wikiManager = (activity?.applicationContext as WikiApplication).wikiManager
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding =  FragmentExploreBinding.inflate(inflater)

        refresher = binding.refresher
        searchCardView = binding.searchView

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        searchCardView!!.setOnClickListener{
            val searchIntent = Intent(context, SearchActivity::class.java)
            context?.startActivity(searchIntent)
        }

        // Sets the adapter of the photosGrid RecyclerView
        binding.exploreItemRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.exploreItemRecycler.adapter = WikiItemAdapter()

        refresher?.setOnRefreshListener {
            getRandomArticle()
        }

        getRandomArticle()

        return binding.root
    }

    private fun getRandomArticle() {
        refresher?.isRefreshing = true

        try {
            val networkManager = NetworkManager()
            if (networkManager.isNetworkAvailable(requireContext())) {
                viewModel.getRandomItems(15)

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