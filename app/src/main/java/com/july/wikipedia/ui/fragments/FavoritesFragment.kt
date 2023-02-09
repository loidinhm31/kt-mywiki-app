package com.july.wikipedia.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.july.wikipedia.databinding.FragmentFavoritesBinding
import com.july.wikipedia.ui.adapters.FavoriteItemAdapter
import com.july.wikipedia.ui.viewmodels.FavoriteItemViewModel


class FavoritesFragment : Fragment() {
    /**
     * One way to delay creation of the viewModel until an appropriate lifecycle method is to use
     * lazy. This requires that viewModel not be referenced before onActivityCreated, which we
     * do in this Fragment.
     */
    private val viewModel: FavoriteItemViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, FavoriteItemViewModel.Factory(activity.application))
            .get(FavoriteItemViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding =  FragmentFavoritesBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the ViewModel
        binding.favoriteItemViewModel = viewModel

        binding.favoriteArticleRecycler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.favoriteArticleRecycler.adapter = FavoriteItemAdapter()

        viewModel.getFavoriteItems()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteItems()
    }
}