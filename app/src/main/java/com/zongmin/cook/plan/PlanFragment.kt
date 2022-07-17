package com.zongmin.cook.plan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zongmin.cook.data.Plan
import com.zongmin.cook.databinding.FragmentPlanBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.login.UserManager
import java.util.*


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
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.toDay.observe(viewLifecycleOwner) {
//            Log.d("hank1","觸發監聽，呼叫getPlanResult")
            viewModel.getPlanResult(UserManager.user.id, it)
        }



        binding.viewModel = viewModel

//        binding.calendarView.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
//
//            Log.d("hank1", "我選了日期 ->${year}年${month + 1} 月$dayOfMonth 日 ")
//            Log.d()
//
//
//        })
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->

            Log.d("hank1", "我選了日期 ->${year}年${month + 1} 月$dayOfMonth 日 ")
//            Log.d()
            val storedDate = GregorianCalendar(year, month, dayOfMonth)
//            Log.d("hank1", "轉換成毫秒是 -> ${storedDate.timeInMillis}")
            viewModel.saveTime = storedDate.timeInMillis
            viewModel.getPlanResult(UserManager.user.id,storedDate.timeInMillis)


        }
        val adapter = PlanAdapter(viewModel)

        binding.recyclerviewPlan.adapter = adapter


//        val dataList = mutableListOf<PlanItem>()
        viewModel.plan.observe(viewLifecycleOwner, Observer {
            val dataList = mutableListOf<PlanItem>()
            val breakfast = mutableListOf<Plan>()
            val lunch = mutableListOf<Plan>()
            val dinner = mutableListOf<Plan>()
//            Log.d("hank1","觸發plan監聽，查看內容 -> $it")
            if(it != null){

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
                dataList.add(PlanItem.Title("早餐"))
                for (i in breakfast) {
                    dataList.add(PlanItem.FullPlan(i))
                }

            }
            if (lunch.size > 0) {
                dataList.add(PlanItem.Title("午餐"))
                for (i in lunch) {
                    dataList.add(PlanItem.FullPlan(i))
                }
            }
            if (dinner.size > 0) {
                dataList.add(PlanItem.Title("晚餐"))
                for (i in dinner) {
                    dataList.add(PlanItem.FullPlan(i))
                }
            }

            Log.d("hank1", "這次計畫是 -> $dataList")
            adapter.submitList(dataList)

            }
        })





        return binding.root
    }


}