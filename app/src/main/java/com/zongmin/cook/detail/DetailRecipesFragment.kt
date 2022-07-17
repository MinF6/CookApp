package com.zongmin.cook.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentDetailRecipesBinding


class DetailRecipesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDetailRecipesBinding.inflate(inflater, container, false)

        Log.d(
            "hank1",
            "我收到了資料 -> ${DetailRecipesFragmentArgs.fromBundle(requireArguments()).recipes}"
        )

        val data = DetailRecipesFragmentArgs.fromBundle(requireArguments()).recipes
        binding.recipes = data

        binding.textDetailTitle.text = data.name

        binding.imageDetailBack.setOnClickListener {
            this.findNavController().navigateUp()
        }

        //食材adapter
        val ingredientAdapter = DetailIngredientAdapter()
        binding.recyclerviewIngredient.adapter = ingredientAdapter

        val viewModel = DetailRecipesViewModel()
        viewModel.getIngredient(data)

        viewModel.ingredient.observe(viewLifecycleOwner, Observer {
            ingredientAdapter.submitList(it)
        })


        //步驟adapter
        val stepAdapter = DetailStepAdapter()
        binding.recyclerviewStep.adapter = stepAdapter
        if (data != null) {
            viewModel.getStep(data)
        }

        viewModel.stepData.observe(viewLifecycleOwner, Observer {
//            Log.d("hank1","看一下拿到的步驟 -> ${it}")
            stepAdapter.submitList(it)
        })




        binding.buttonDetailToEdit.setOnClickListener {
            //到時候要帶值給編輯
            findNavController().navigate(NavigationDirections.navigateToEditRecipesFragment(data))
        }


        return binding.root

    }


}