package com.zongmin.cook.management

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zongmin.cook.databinding.FragmentManagementBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.login.UserManager


class ManagementFragment : Fragment() {

    private val viewModel by viewModels<ManagementViewModel> { getVmFactory() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentManagementBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        val adapter = ManagementAdapter(viewModel)
        binding.recyclerviewManagement.adapter = adapter

        viewModel.management.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.recyclerviewManagement.setItemViewCacheSize(it.size)
                adapter.submitList(it)
            } else {
                binding.textManagementNeed.text = "需購食材0項"
                adapter.submitList(null)
            }
        }

        viewModel.quantity.observe(viewLifecycleOwner) {
            binding.textManagementNeed.text = "需購食材" + it + "項"
            if (it == 0) {
                binding.textManagementNeed.setTextColor(Color.rgb(0, 170, 0))
            } else {
                binding.textManagementNeed.setTextColor(Color.rgb(255, 0, 0))
            }
        }

        binding.textManagementDate.text = viewModel.getToday()
        viewModel.time.value?.let { time ->
            viewModel.getManagementResult(
                UserManager.user.id,
                time
            )
        }

        binding.buttonManagementYesterday.setOnClickListener {
            binding.textManagementDate.text = viewModel.getYesterday()
            viewModel.time.value?.let { time ->
                viewModel.getManagementResult(
                    UserManager.user.id,
                    time
                )
            }
        }

        binding.buttonManagementTomorrow.setOnClickListener {
            binding.textManagementDate.text = viewModel.getTomorrow()
            viewModel.time.value?.let { time ->
                viewModel.getManagementResult(
                    UserManager.user.id,
                    time
                )
            }
        }

        binding.buttonManagementToday.setOnClickListener {
            binding.textManagementDate.text = viewModel.getToday()
            viewModel.time.value?.let { time ->
                viewModel.getManagementResult(
                    UserManager.user.id,
                    time
                )
            }
        }

        val dayTime = 24 * 60 * 60 * 1000L
        binding.buttonManagementThreeDay.setOnClickListener {
            binding.textManagementDate.text = viewModel.getThreeDay()
            viewModel.time.value?.let { time ->
                viewModel.getPeriodManagementResult(
                    UserManager.user.id,
                    time, time + (dayTime * 3)
                )
            }
        }

        binding.buttonManagementWeek.setOnClickListener {
            binding.textManagementDate.text = viewModel.getWeek()
            binding.recyclerviewManagement.recycledViewPool.clear()
            viewModel.time.value?.let { time ->
                viewModel.getPeriodManagementResult(
                    UserManager.user.id,
                    time, time + (dayTime * 7)
                )
            }
        }
        return binding.root
    }

}