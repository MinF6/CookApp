package com.zongmin.cook.social

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zongmin.cook.databinding.FragmentSocialBinding
import com.zongmin.cook.ext.getVmFactory
import androidx.lifecycle.Observer


class SocialFragment : Fragment() {

    private val viewModel by viewModels<SocialViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSocialBinding.inflate(inflater, container, false)

        viewModel.getRecipesResult()

        val adapter = SocialAdapter()

        binding.recyclerviewSocial.adapter = adapter

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            Log.d("hank1", "觀察一下拿到的 => $it")
            adapter.submitList(it)
        })





        return binding.root
    }


}