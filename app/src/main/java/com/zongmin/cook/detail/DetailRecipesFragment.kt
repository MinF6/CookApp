package com.zongmin.cook.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentDetailRecipesBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.login.UserManager


class DetailRecipesFragment : Fragment() {

    private val viewModel by viewModels<DetailRecipesViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDetailRecipesBinding.inflate(inflater, container, false)

        val data = DetailRecipesFragmentArgs.fromBundle(requireArguments()).recipe

        data.let{
            binding.recipe = data
            viewModel.getSteps(data)
            viewModel.getIngredients(data)
        }


        binding.imageDetailBack.setOnClickListener {
            this.findNavController().navigateUp()
        }
        if (UserManager.user.id == data.author) {
            binding.buttonDetailToEdit.visibility = View.VISIBLE
            binding.switchDetailPublic.visibility = View.VISIBLE
        } else {
            binding.buttonDetailToEdit.visibility = View.GONE
            binding.switchDetailPublic.visibility = View.GONE
        }



        val ingredientAdapter = DetailIngredientAdapter()
        binding.recyclerviewIngredient.adapter = ingredientAdapter
        viewModel.ingredients.observe(viewLifecycleOwner){
            ingredientAdapter.submitList(it)
        }


        val stepAdapter = DetailStepAdapter()
        binding.recyclerviewStep.adapter = stepAdapter

        viewModel.steps.observe(viewLifecycleOwner) {
            it?.let {
                stepAdapter.submitList(it)
            }
        }

        binding.switchDetailPublic.isChecked = data.public
        binding.switchDetailPublic.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setPublicRecipes(isChecked, data.id)
        }

        binding.buttonDetailToEdit.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToEditRecipesFragment(data))
        }

        return binding.root

    }
}