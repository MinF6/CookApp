package com.zongmin.cook.follow

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zongmin.cook.databinding.FragmentFollowBinding
import com.zongmin.cook.ext.getVmFactory


class FollowFragment : Fragment() {

    private val viewModel by viewModels<FollowViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFollowBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = FollowAdapter(FollowAdapter.OnClickListener{

        })


        viewModel.user.observe(viewLifecycleOwner){
            it?.let{
                adapter.submitList(it)
            }
        }

        binding.recyclerviewFollow.adapter = adapter


        return binding.root
    }


}