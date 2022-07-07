package com.zongmin.cook.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentRecipesBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.recipes.item.RecipesItemViewModel
import java.text.SimpleDateFormat
import java.util.*


class RecipesFragment : Fragment() {

    private val viewModel by viewModels<ReccipesViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.radioGroupRecipes.setOnCheckedChangeListener { _, i ->
            val checkedMeals = binding.radioGroupRecipes.findViewById<RadioButton>(i).text.toString()
            Log.d("hank1","radio改變，目前選的是 -> ${checkedMeals}")
            viewModel.threeMeals.value = checkedMeals
        }


        binding.viewpagerRecipes.let {
            binding.tabsRecipes.setupWithViewPager(it)
            binding.tabsRecipes.tabMode = TabLayout.MODE_SCROLLABLE;
//                val adapter = RecipesAdapter(childFragmentManager)
            it.adapter = RecipesAdapter(childFragmentManager)
//                it.adapter = adapter
            it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabsRecipes))
        }

        binding.buttonRecipesDialog.text = SimpleDateFormat("MM/dd").format(Date())

        binding.buttonNavNew.setOnClickListener {
//                this.findNavController().navigate(MainNavigationDirections.navigateToArticleFragment())
            findNavController().navigate(NavigationDirections.navigateToEditRecipesFragment(null))
        }


        //傳值
        binding.buttonReicpesSearch.setOnClickListener {

            val result = binding.edittextRecipesSearch.text.toString()

//            childFragmentManager.setFragmentResult("RecipesCard", bundleOf("bundleKey" to result))

            viewModel.searchText.value = result
//            Log.d("hank1", "按了搜尋按鈕，傳送了 -> ${bundleOf("bundleKey" to result).getString("bundleKey")}")

        }

        binding.buttonRecipesClear.setOnClickListener {
            binding.edittextRecipesSearch.setText("")
            val result = binding.edittextRecipesSearch.text.toString()
//            childFragmentManager.setFragmentResult("RecipesCard", bundleOf("bundleKey" to result))
            viewModel.searchText.value = result
        }

        binding.buttonRecipesDialog.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToDialogPlan())

        }

//        viewModel.searchText.observe(viewLifecycleOwner) {
//            Log.d("hank2", "viewModel.searchText.observe, it->${it}")
//        }


        return binding.root

    }


}