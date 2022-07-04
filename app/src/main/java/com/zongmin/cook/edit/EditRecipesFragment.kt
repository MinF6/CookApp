package com.zongmin.cook.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.R
import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Step
import com.zongmin.cook.data.Summary
import com.zongmin.cook.data.ToolType
import com.zongmin.cook.databinding.FragmentEditRecipesBinding
import com.zongmin.cook.databinding.ItemEditIngredientBinding
import com.zongmin.cook.databinding.ItemEditStepBinding
import com.zongmin.cook.ext.getVmFactory


class EditRecipesFragment : Fragment() {


    //    var addIngredient: Button? = null
//    var addStep: Button? = null
    var ingredientList: LinearLayout? = null
    var stepList: LinearLayout? = null
//    var ingredientName: EditText? = null
//    var quantity: EditText? = null
//    var unit: EditText? = null

    private val viewModel by viewModels<EditRecipesViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentEditRecipesBinding.inflate(inflater, container, false)
        val binding2 = ItemEditIngredientBinding.inflate(inflater, container, false)
        val binding3 = ItemEditStepBinding.inflate(inflater, container, false)

//        val addIngredient = binding.buttonNewIngredient
//        val ingredientName = binding2.itemEdittextIngredientName
//        val quantity = binding2.itemEdittextQuantity
//        val unit = binding2.itemEdittextUnit
//        val ingredientList = binding.editIngredientList


//        addIngredient = binding.buttonNewIngredient
        val ingredientName = binding2.itemEdittextIngredientName
        val quantity = binding2.itemEdittextQuantity
        val unit = binding2.itemEdittextUnit
        ingredientList = binding.editIngredientList
        stepList = binding.editStepList
        val depiction = binding3.itemEdittextStepDepiction

//        addStep =

        binding.buttonNewStep.setOnClickListener {
            addStepView()
        }


//        binding.buttonNewStep.setOnClickListener {
//            Log.d("hank1","案到了新增步驟")
//            addStepView()
//
//        }

//        val context = this

        binding.buttonNewIngredient.setOnClickListener {
            addView()

        }




        binding.buttonEditSave.setOnClickListener {
            val newRecipes = Summary()
            val listNewIngredient = mutableListOf<Ingredient>()
//            val newStep = Step()
            val listnewStep = mutableListOf<Step>()

            //取得步驟內容
            var sequence = 1
            for (i in 0 until stepList!!.childCount) {
                if (stepList!!.getChildAt(i) is LinearLayoutCompat) {
                    val ll = stepList!!.getChildAt(i) as LinearLayoutCompat
//                    var itemName = ""
//                    var itemQuantity = ""
//                    var itemUnit = ""

                    for (j in 0 until ll.childCount) {
                        if (ll.getChildAt(j) is EditText) {
                            val et = ll.getChildAt(j) as EditText
                            if (et.id == depiction!!.id) {
                                Log.d("hank1", "這組depiction放了 => ${et.text}")
//                                itemUnit = et.text.toString()
                                listnewStep.add(
                                    Step(
                                        "",
                                        sequence.toString(),
                                        listOf("https://tokyo-kitchen.icook.network/uploads/recipe/cover/407229/1e9aa981b9a4a97f.jpg"),
                                        et.text.toString(),
                                        ToolType()
                                    )
                                )
                                sequence++
                                Log.d("hank1", "檢查目前的sequence結果 => ${sequence}")
                                Log.d("hank1", "檢查目前的listnewStep結果 => ${listnewStep}")
                            }
                        }
                    }
                }
            }


            //取得食材內容
            for (i in 0 until ingredientList!!.childCount) {
                if (ingredientList!!.getChildAt(i) is LinearLayoutCompat) {
                    val ll = ingredientList!!.getChildAt(i) as LinearLayoutCompat
                    var itemName = ""
                    var itemQuantity = ""
                    var itemUnit = ""
                    for (j in 0 until ll.childCount) {
                        if (ll.getChildAt(j) is EditText) {
                            val et = ll.getChildAt(j) as EditText
                            if (et.id == ingredientName!!.id) {
                                Log.d("hank1", "這組ingredientName放了 => ${et.text}")
                                itemName = et.text.toString()
                            }
                            if (et.id == quantity!!.id) {
                                Log.d("hank1", "這組quantity放了 => ${et.text}")
                                itemQuantity = et.text.toString()
                            }
                            if (et.id == unit!!.id) {
                                Log.d("hank1", "這組unit放了 => ${et.text}")
                                itemUnit = et.text.toString()
                                listNewIngredient.add(
                                    Ingredient("", itemName, itemQuantity, itemUnit)
                                )
                                Log.d("hank1", "檢查目前的listNewIngredient結果 => ${listNewIngredient}")
                            }
                        }
                    }
                }
            }






            newRecipes.name = binding.edittextEditName.text.toString()
            newRecipes.category = binding.spinnerEditCategory.selectedItem.toString()
            newRecipes.serving = binding.edittextServing.text.toString().toInt()
//            newIngredient.ingredientName = binding.edittextIngredientName.text.toString()
//            newIngredient.quantity = binding.edittextQuantity.text.toString()
//            newIngredient.unit = binding.edittextUnit.text.toString()
//            newRecipes.ingredient = listOf(newIngredient)

//            newStep.depiction = binding.edittextEditStep.text.toString()
//            newStep.images =
//                listOf("https://tokyo-kitchen.icook.network/uploads/recipe/cover/407229/1e9aa981b9a4a97f.jpg")
//            newStep.sequence = "1"


            newRecipes.mainImage =
                "https://tokyo-kitchen.icook.network/uploads/recipe/cover/406300/ec1128b22f4092d6.jpg"
            newRecipes.cookingTime = binding.edittextEditCookTime.text.toString()
            newRecipes.author = "W5bXC4hAbvs5zOYY7i5R"
            newRecipes.remark = binding.edittextEditRemark.text.toString()

//            viewModel.create(newRecipes, listOf(newIngredient), listOf(newStep))
//            viewModel.create(newRecipes, listNewIngredient, listOf(newStep))
            viewModel.create(newRecipes, listNewIngredient, listnewStep)

            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
        }

        binding.buttonEditCancel.setOnClickListener {
            findNavController().navigate(NavigationDirections.navigateToRecipesFragment())
        }



        return binding.root

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_edit_recipes, container, false)
    }

    //    private fun addView(inflater: LayoutInflater, container: ViewGroup?) {
    private fun addView() {
//        val binding2 = ItemEditIngredientBinding.inflate(inflater, container, false)
        val aa: View = layoutInflater.inflate(R.layout.item_edit_ingredient, null, false)
        val cross: ImageView = aa.findViewById<View>(R.id.item_cross) as ImageView
//        cross2.setVisibility(View.VISIBLE)
        cross.visibility = View.VISIBLE
//        val cross = binding2.itemCross
//        cross.visibility = View.VISIBLE

        cross.setOnClickListener {
            removeView(aa)
        }


        ingredientList?.addView(aa)

//        val cross: ImageView = aa.findViewById<View>(R.id.cross) as ImageView
//        cross.setVisibility(View.VISIBLE)

    }

    private fun removeView(view: View) {
        ingredientList?.removeView(view)

    }

    private fun addStepView() {
        val bb: View = layoutInflater.inflate(R.layout.item_edit_step, null, false)
        val cross: ImageView = bb.findViewById<View>(R.id.item_step_cross) as ImageView
        cross.visibility = View.VISIBLE


        cross.setOnClickListener {
            removeStepView(bb)
        }

        stepList?.addView(bb)

    }

    private fun removeStepView(view: View) {
        stepList?.removeView(view)

    }


}
