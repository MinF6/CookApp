package com.zongmin.cook.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zongmin.cook.databinding.FragmentPlanBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.recipes.item.RecipesItemViewModel


class PlanFragment : Fragment() {


    private val viewModel by viewModels<PlanViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentPlanBinding.inflate(inflater, container, false)


        viewModel.getPlanResult()

        binding.calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->

            Log.d("hank1", "我選了日期 ->${year}年${month + 1} 月$dayOfMonth 日 ")


        })
        val adapter = PlanAdapter()

        binding.recyclerviewPlan.adapter = adapter


        val dataList = mutableListOf<PlanItem>()
        viewModel.plan.observe(viewLifecycleOwner, Observer {

            Log.d("hank1", "資料有進來了")
            for (plan in it) {
                val item1 = PlanItem.Title(plan.threeMeals)
                dataList.add(item1)

                val item2 = PlanItem.FullPlan(plan.planContent)
                dataList.add(item2)

            }
            adapter.submitList(dataList)


        })

        return binding.root
    }


}