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
//dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val recipesViewModel =
            ViewModelProvider(requireParentFragment()).get(RecipesViewModel::class.java)
//        binding.textDialogDate.text = viewModel.getToday()


        //昨天鍵
        binding.buttonDialogYesterday.setOnClickListener {
//            binding.textDialogDate.text = viewModel.getYesterday()
//            recipesViewModel.date.value = viewModel.getYesterday()
            viewModel.getYesterday()
        }

        //明天鍵
        binding.buttonDialogTomorrow.setOnClickListener {
//            binding.textDialogDate.text = viewModel.getTomorrow()
//            recipesViewModel.date.value = viewModel.getYesterday()
            viewModel.getTomorrow()
        }


        viewModel.date.observe(viewLifecycleOwner) {
            Log.d("hank1", "檢查改變後的日期毫秒 -> $it")
            recipesViewModel.date.value = it
            binding.textDialogDate.text = SimpleDateFormat("yyyy/MM/dd").format(Date(it))

        }




//        viewModel.getPlanResult()

        val adapter = DialogPlanAdapter(viewModel)

        binding.recyclerviewDialog.adapter = adapter

        viewModel.plan.observe(viewLifecycleOwner, Observer {
            val dataList = mutableListOf<DialogItem>()
            val breakfast = mutableListOf<Plan>()
            val lunch = mutableListOf<Plan>()
            val dinner = mutableListOf<Plan>()


//            for (plan in it) {
//                val item1 = DialogItem.Title(plan.threeMeals)
//                dataList.add(item1)
//
//                val item2 = DialogItem.FullPlan(plan)
//                dataList.add(item2)
//            }

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

//            val item1 = DialogItem.Title(plan.threeMeals)
//            dataList.add(item1)
//            val item2 = DialogItem.FullPlan(plan)
//            dataList.add(item2)


//            Log.d("hank1", "安排完的結果 => $dataList")
            adapter.submitList(dataList)

        })


        return binding.root
    }


}