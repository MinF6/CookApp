package com.zongmin.cook.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import com.zongmin.cook.databinding.FragmentDialogPlanBinding
import com.zongmin.cook.ext.getVmFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zongmin.cook.data.Plan
import com.zongmin.cook.recipes.RecipesViewModel
import java.text.SimpleDateFormat
import java.util.*


class DialogPlan : AppCompatDialogFragment() {

    private val viewModel by viewModels<DialogPlanViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDialogPlanBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val recipesViewModel =
            ViewModelProvider(requireParentFragment()).get(RecipesViewModel::class.java)

        binding.buttonDialogYesterday.setOnClickListener {
            viewModel.getYesterday()
        }

        //明天鍵
        binding.buttonDialogTomorrow.setOnClickListener {
            viewModel.getTomorrow()
        }


        viewModel.date.observe(viewLifecycleOwner) {
            recipesViewModel.date.value = it
            binding.textDialogDate.text = SimpleDateFormat("yyyy/MM/dd").format(Date(it))
        }


        val adapter = DialogPlanAdapter(viewModel)

        binding.recyclerviewDialog.adapter = adapter

        viewModel.plan.observe(viewLifecycleOwner, Observer {
            val dataList = mutableListOf<DialogItem>()
            val breakfast = mutableListOf<Plan>()
            val lunch = mutableListOf<Plan>()
            val dinner = mutableListOf<Plan>()

            for (plan in it) {
                when (plan.threeMeals) {
                    "早餐" -> {
                        breakfast.add(plan)
                    }
                    "午餐" -> {
                        lunch.add(plan)
                    }
                    "晚餐" -> {
                        dinner.add(plan)
                    }
                }


            }
            if (breakfast.size > 0) {
                dataList.add(DialogItem.Title("早餐"))
                for (i in breakfast) {
                    dataList.add(DialogItem.FullPlan(i))
                }

            }
            if (lunch.size > 0) {
                dataList.add(DialogItem.Title("午餐"))
                for (i in lunch) {
                    dataList.add(DialogItem.FullPlan(i))
                }
            }
            if (dinner.size > 0) {
                dataList.add(DialogItem.Title("晚餐"))
                for (i in dinner) {
                    dataList.add(DialogItem.FullPlan(i))
                }
            }
            adapter.submitList(dataList)
        })

        return binding.root
    }


}