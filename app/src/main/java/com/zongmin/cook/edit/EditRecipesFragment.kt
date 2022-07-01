package com.zongmin.cook.edit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.Step
import com.zongmin.cook.databinding.FragmentEditRecipesBinding
import com.zongmin.cook.ext.getVmFactory


class EditRecipesFragment : Fragment() {


    private val viewModel by viewModels<EditRecipesViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditRecipesBinding.inflate(inflater, container, false)



        binding.buttonEditSave.setOnClickListener {
            val newRecipes = Recipes()
            val newIngredient = Ingredient()
            val newStep = Step()
            newRecipes.name = binding.edittextEditName.text.toString()
            newRecipes.category = binding.spinnerEditCategory.selectedItem.toString()
            newRecipes.serving = binding.edittextServing.text.toString().toInt()
            newIngredient.ingredientName = binding.edittextIngredientName.text.toString()
            newIngredient.quantity = binding.edittextQuantity.text.toString()
            newIngredient.unit = binding.edittextUnit.text.toString()
//            newRecipes.ingredient = listOf(newIngredient)
            newStep.depiction = binding.edittextEditStep.text.toString()
            newStep.images = listOf("https://tokyo-kitchen.icook.network/uploads/recipe/cover/407229/1e9aa981b9a4a97f.jpg")
            newStep.sequence = "1"
//            newRecipes.step = listOf(newStep)
            newRecipes.mainImage = "https://tokyo-kitchen.icook.network/uploads/recipe/cover/407229/1e9aa981b9a4a97f.jpg"
            newRecipes.cookingTime = binding.edittextEditCookTime.text.toString()
            newRecipes.author = "W5bXC4hAbvs5zOYY7i5R"
            newRecipes.remark = binding.edittextEditRemark.text.toString()

            viewModel.create(newRecipes, listOf(newIngredient), listOf(newStep))




        }





        return binding.root



        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_edit_recipes, container, false)
    }




}
