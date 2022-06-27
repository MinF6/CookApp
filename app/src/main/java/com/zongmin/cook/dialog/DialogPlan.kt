package com.zongmin.cook.dialog

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

        binding.textDialogDate.text = viewModel.getToday()

        //昨天鍵
        binding.buttonDialogYesterday.setOnClickListener {
            binding.textDialogDate.text = viewModel.getYesterday()
        }

        //明天鍵
        binding.buttonDialogTomorrow.setOnClickListener {
            binding.textDialogDate.text = viewModel.getTomorrow()
        }




        viewModel.getPlanResult()

        val adapter = DialogPlanAdapter()

        binding.recyclerviewDialog.adapter = adapter

        val dataList = mutableListOf<DialogItem>()
        viewModel.plan.observe(viewLifecycleOwner, Observer {
            for (plan in it) {
                val item1 = DialogItem.Title(plan.threeMeals)
                dataList.add(item1)

                val item2 = DialogItem.FullPlan(plan.planContent)
                dataList.add(item2)

            }
            Log.d("hank1", "安排完的結果 => $dataList")
            adapter.submitList(dataList)
        })


        return binding.root
    }


}