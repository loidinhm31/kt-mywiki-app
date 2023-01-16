package com.july.wikipedia.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.july.wikipedia.R
import com.july.wikipedia.databinding.FragmentExploreBinding
import com.july.wikipedia.managers.NetworkManager
import com.july.wikipedia.ui.activities.SearchActivity
import com.july.wikipedia.ui.adapters.WikiItemAdapter
import com.july.wikipedia.ui.viewmodels.ItemViewModel


class ExploreFragment : Fragment() {

    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: ItemViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, ItemViewModel.Factory(activity.application))
            .get(ItemViewModel::class.java)
    }

    private var searchCardView: CardView? = null

    private var refresher: SwipeRefreshLayout? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding =  FragmentExploreBinding.inflate(inflater)

        refresher = binding.refresher
        searchCardView = binding.searchView

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ViewModel
        binding.itemViewModel = viewModel

        searchCardView!!.setOnClickListener{
            val searchIntent = Intent(context, SearchActivity::class.java)
            context?.startActivity(searchIntent)
        }

        // Sets the adapter of the RecyclerView
        binding.exploreItemRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.exploreItemRecycler.adapter = WikiItemAdapter()

        refresher?.setOnRefreshListener {
            getRandomItems(binding)
        }

        getRandomItems(binding)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getRandomItems(binding: FragmentExploreBinding) {
        refresher?.isRefreshing = true

        try {
            val networkManager = NetworkManager()
            if (networkManager.isNetworkAvailable(requireContext())) {
                viewModel.refreshAndSaveData(15)

            } else {
                viewModel.itemList.observe(this.viewLifecycleOwner) { items ->
                    items.let {
                        (binding.exploreItemRecycler.adapter as WikiItemAdapter).submitList(it)
                    }
                }
                val toast: Toast = Toast.makeText(context, getString(R.string.refresh_page), Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
            }

            refresher?.isRefreshing = false
        } catch (e: Exception) {
            // Show alert
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(e.message).setTitle("ERROR!").show()
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.refreshAndSaveData(15)
    }
}