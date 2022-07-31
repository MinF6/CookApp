package com.zongmin.cook.recipes.item

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.data.Management
import com.zongmin.cook.databinding.FragmentRecipesItemBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.login.UserManager
import com.zongmin.cook.recipes.RecipesViewModel
import com.zongmin.cook.recipes.RecipesTypeFilter
import com.zongmin.viewpagercards.CardPagerAdapter
import com.zongmin.viewpagercards.ShadowTransformer


class RecipesItemFragment(private val recipesType: RecipesTypeFilter) : Fragment() {

    private val viewModel by viewModels<RecipesItemViewModel> { getVmFactory(recipesType) }
    private var mViewPager: ViewPager? = null
    private var mCardAdapter: CardPagerAdapter? = null
    private var mCardShadowTransformer: ShadowTransformer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecipesItemBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val recipesViewModel =
            ViewModelProvider(requireParentFragment()).get(RecipesViewModel::class.java)
        mViewPager = binding.viewPager
        mCardAdapter =
            CardPagerAdapter(viewModel, recipesViewModel, CardPagerAdapter.OnClickListener {
                viewModel.setPlan(
                    recipesViewModel.threeMeals.value!!,
                    it.name,
                    it.id,
                    it.mainImage,
                    it.category,
                    recipesViewModel.date.value!!,
                    it
                )
            })

        viewModel.planId.observe(viewLifecycleOwner) {
            val ingredientList = viewModel.itemRecipe.value?.ingredient
            if (ingredientList != null) {
                for (ingredient in ingredientList) {

                    val newManagement = viewModel.itemRecipe.value?.let { it1 ->

                        Management(
                            " ",
                            UserManager.user.id,
                            it,
                            recipesViewModel.threeMeals.value!!,
                            ingredient.ingredientName,
                            it1.name,
                            ingredient.quantity,
                            ingredient.unit,
                            recipesViewModel.date.value!!
                        )
                    }
                    if (newManagement != null) {
                        viewModel.createManagementResult(newManagement)
                    }
                }
            }

        }

        binding.viewModel = viewModel
        viewModel.recipe.observe(viewLifecycleOwner, Observer {

            mCardAdapter!!.remakeData()
            if (it != null) {
                binding.imageRecipesNull.visibility = View.GONE
                for (i in it) {
                    mCardAdapter!!.addCardItem(i)
                }
                mCardShadowTransformer = ShadowTransformer(mViewPager!!, mCardAdapter!!)
                mCardShadowTransformer!!.enableScaling(true)
                mViewPager!!.adapter = mCardAdapter
                mViewPager!!.setPageTransformer(false, mCardShadowTransformer)
                mViewPager!!.offscreenPageLimit = 3

            } else {
                binding.imageRecipesNull.visibility = View.VISIBLE
            }


        })

        viewModel.navigateToPlan.observe(viewLifecycleOwner) {
            Toast.makeText(context, "安排計畫成功", Toast.LENGTH_LONG).show()
        }



        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer {
            it?.let {
                findNavController().navigate(NavigationDirections.navigateToDetailRecipesFragment(it))
                viewModel.onDetailNavigated()
            }
        })

        val parentViewModel =
            ViewModelProvider(requireParentFragment()).get(RecipesViewModel::class.java)

        parentViewModel.searchText.observe(viewLifecycleOwner) {
            mCardAdapter!!.remakeData()
            viewModel.setRecipesKey(recipesType.value, it)

        }


        return binding.root
    }
}
