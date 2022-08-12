package com.zongmin.cook.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zongmin.cook.databinding.FragmentPlanBinding
import com.zongmin.cook.ext.getVmFactory
import com.zongmin.cook.login.UserManager
import java.util.*


class PlanFragment : Fragment() {

    private val viewModel by viewModels<PlanViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentPlanBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.today.observe(viewLifecycleOwner) {
            viewModel.getPlanResult(UserManager.user.id, it)
        }

        binding.viewModel = viewModel
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val storedDate = GregorianCalendar(year, month, dayOfMonth)
            viewModel.getPlanResult(UserManager.user.id, storedDate.timeInMillis)
        }

        val adapter = PlanAdapter(viewModel)
        binding.recyclerviewPlan.adapter = adapter

        viewModel.plans.observe(viewLifecycleOwner) {
            it.let {
                viewModel.setSortedPlan(it)
            }
        }

        viewModel.sortedPlans.observe(viewLifecycleOwner) {
            it.let {
                adapter.submitList(it)
            }
        }

        viewModel.deletePlanResult.observe(viewLifecycleOwner) {
            viewModel.getSpecifyManagementResult(it)
        }

        viewModel.managements.observe(viewLifecycleOwner) {
            for (management in it) {
                viewModel.deleteManagement(management.id)
            }
        }
        return binding.root
    }

}