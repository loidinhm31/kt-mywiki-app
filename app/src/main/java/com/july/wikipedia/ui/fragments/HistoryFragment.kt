package com.july.wikipedia.ui.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.july.wikipedia.R
import com.july.wikipedia.databinding.FragmentFavoritesBinding
import com.july.wikipedia.databinding.FragmentHistoryBinding
import com.july.wikipedia.databinding.HistoryItemBinding
import com.july.wikipedia.ui.adapters.FavoriteItemAdapter
import com.july.wikipedia.ui.adapters.HistoryItemAdapter
import com.july.wikipedia.ui.viewmodels.FavoriteItemViewModel
import com.july.wikipedia.ui.viewmodels.HistoryItemViewModel


class HistoryFragment : Fragment() {
    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: HistoryItemViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, HistoryItemViewModel.Factory(activity.application))
            .get(HistoryItemViewModel::class.java)
    }

    init {
        // tell parent activity of the fragment, we are going to use this fragment
        // ...and show menu option of this fragment
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHistoryBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ViewModel
        binding.historyItemViewModel = viewModel

        binding.historyArticleRecycler.layoutManager = LinearLayoutManager(context)
        binding.historyArticleRecycler.adapter = HistoryItemAdapter()

        viewModel.getHistoryItems()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHistoryItems()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_clear_history) {
            // Show confirmation alert
            val alertDialog: AlertDialog? = activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.apply {
                    setTitle("Do you want to clear your history?")
                    setPositiveButton("Yes",
                        DialogInterface.OnClickListener { _, id ->
                            viewModel.clearHistoryItems()
                        })
                    setNegativeButton("No",
                        DialogInterface.OnClickListener { _, id ->
                            // Nothing
                        })
                }
                // Create the AlertDialog
                builder.create()
                builder.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}