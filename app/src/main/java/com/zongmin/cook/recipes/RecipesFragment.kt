package com.zongmin.cook.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentRecipesBinding
import com.zongmin.cook.ext.getVmFactory
import java.text.SimpleDateFormat
import java.util.*


class RecipesFragment : Fragment() {

    private val viewModel by viewModels<RecipesViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.radioGroupRecipes.setOnCheckedChangeListener { _, i ->
            val checkedMeals =
                binding.radioGroupRecipes.findViewById<RadioButton>(i).text.toString()
            Log.d("hank1", "radio改變，目前選的是 -> ${checkedMeals}")
            viewModel.threeMeals.value = checkedMeals
        }


        binding.viewpagerRecipes.let {
            binding.tabsRecipes.setupWithViewPager(it)
            binding.tabsRecipes.tabMode = TabLayout.MODE_SCROLLABLE;
            it.adapter = RecipesAdapter(childFragmentManager)
            it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabsRecipes))
        }

        viewModel.date.observe(viewLifecycleOwner) {
            binding.buttonRecipesDialog.text = SimpleDateFormat("MM/dd").format(Date(it))
            binding.buttonRecipesDate.text = SimpleDateFormat("MM/dd").format(Date(it))
        }

        binding.buttonReicpesSearch.setOnClickListener {
            val result = binding.edittextRecipesSearch.text.toString()
            viewModel.searchText.value = result
        }

        binding.buttonRecipesClear.setOnClickListener {
            binding.edittextRecipesSearch.setText("")
            val result = binding.edittextRecipesSearch.text.toString()
            viewModel.searchText.value = result
        }

        binding.buttonRecipesDialog.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToDialogPlan())

        }

        binding.buttonRecipesDate.setOnClickListener {
            context?.let { it1 -> viewModel.showDateTimeDialog(it1) }

        }
        return binding.root

    }


}