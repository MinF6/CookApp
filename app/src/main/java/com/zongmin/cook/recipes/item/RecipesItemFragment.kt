package com.zongmin.cook.recipes.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zongmin.cook.R
import com.zongmin.cook.databinding.FragmentRecipesItemBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.recipes.RecipesTypeFilter


class RecipesItemFragment(private val recipesType: RecipesTypeFilter) : Fragment() {

    /**
     * Lazily initialize our [CatalogItemViewModel].
     */
    private val viewModel by viewModels<RecipesItemViewModel> { getVmFactory(recipesType) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecipesItemBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

//        binding.viewModel = viewModel

//        binding.recyclerCatalogItem.adapter = PagingAdapter(
//            PagingAdapter.OnClickListener {
//                viewModel.navigateToDetail(it)
//            }
//        )

//        viewModel.navigateToDetail.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
//                    findNavController().navigate(NavigationDirections.navigateToDetailFragment(it))
//                    viewModel.onDetailNavigated()
//                }
//            }
//        )

//        viewModel.pagingDataProducts.observe(
//            viewLifecycleOwner,
//            Observer {
//                (binding.recyclerCatalogItem.adapter as PagingAdapter).submitList(it)
//            }
//        )

//        binding.layoutSwipeRefreshCatalogItem.setOnRefreshListener {
//            viewModel.refresh()
//        }

//        viewModel.status.observe(
//            viewLifecycleOwner,
//            Observer {
//                it?.let {
//                    if (it != LoadApiStatus.LOADING)
//                        binding.layoutSwipeRefreshCatalogItem.isRefreshing = false
//                }
//            }
//        )

        return binding.root
    }
}
