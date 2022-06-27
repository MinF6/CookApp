package com.zongmin.cook.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.zongmin.cook.databinding.FragmentProfileBinding
import com.zongmin.cook.ext.getVmFactory



class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel.getUserResult()

        val adapter = ProfileAdapter()

        binding.recyclerviewProfile.adapter = adapter

        viewModel.user.observe(viewLifecycleOwner, Observer {
//            Log.d("hank1","show user -> $it")
            binding.user = it
            binding.textProfileName.text = it.name
            binding.textProfileFans.text = it.fans.size.toString()
            binding.textProfileFollows.text = it.follows.size.toString()
        })

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            Log.d("hank1","show 拿到的食譜，List<Recipes> -> $it")
            adapter.submitList(it)
        })






        return binding.root
    }


}