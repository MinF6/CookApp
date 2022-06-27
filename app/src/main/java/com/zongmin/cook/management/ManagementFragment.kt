package com.zongmin.cook.management

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zongmin.cook.databinding.FragmentManagementBinding
import com.zongmin.cook.ext.getVmFactory


class ManagementFragment : Fragment() {

    private val viewModel by viewModels<ManagementViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentManagementBinding.inflate(inflater, container, false)

        val adapter = ManagementAdapter()

        binding.recyclerviewManagement.adapter = adapter

        viewModel.getManagementResult()

        viewModel.management.observe(viewLifecycleOwner, Observer {
            Log.d("hank1","看一下拿到的食材 -> ${it}")
            binding.textManagementNeed.text = "需購食材" + it.size.toString() + "項"
            adapter.submitList(it)

        })

//        val day = 24 * 60 * 60 * 1000L
//
//        Log.d(
//            "hank1",
//            "測試時間的加減 ${SimpleDateFormat("yyyy/MM/dd").format(Date(System.currentTimeMillis() - day))}"
//        )
//        Log.d("hank1", "測試時間的加減 ${System.currentTimeMillis()}")


        binding.textManagementDate.text = viewModel.getToday()



        //昨天鍵
        binding.buttonManagementYesterday.setOnClickListener {
            binding.textManagementDate.text = viewModel.getYesterday()
        }

        //明天鍵
        binding.buttonManagementTomorrow.setOnClickListener {
            binding.textManagementDate.text = viewModel.getTomorrow()
        }

        //本日鍵
        binding.buttonManagementToday.setOnClickListener {
            binding.textManagementDate.text = viewModel.getToday()
        }

        //三日鍵
        binding.buttonManagementThreeDay.setOnClickListener {
            binding.textManagementDate.text = viewModel.getThreeDay()
        }

        //一周鍵
        binding.buttonManagementWeek.setOnClickListener {
            binding.textManagementDate.text = viewModel.getWeek()
        }





        return binding.root
    }


}