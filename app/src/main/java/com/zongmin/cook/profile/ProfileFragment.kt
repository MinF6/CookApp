package com.zongmin.cook.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.zongmin.cook.NavigationDirections
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
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.getUserResult()

        val adapter = ProfileAdapter(ProfileAdapter.OnClickListener{
            viewModel.navigateToDetail(it)
        })

        binding.recyclerviewProfile.adapter = adapter

        viewModel.user.observe(viewLifecycleOwner, Observer {
            Log.d("hank1","拿到了什麼User資料? -> $it")

            binding.user = it
            binding.textProfileName.text = it.name
            binding.textProfileFollows.text = it.follows.size.toString()
            binding.textProfileFans.text = it.fans.size.toString()
            Log.d("hank1","粉絲數量 -> ${it.fans.size}")
            Log.d("hank1","粉絲內容 -> ${it.fans}")
            binding.textProfileIntroduce.text = it.introduce

        })

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner){
            if(it != null){
                findNavController().navigate(NavigationDirections.navigateToDetailRecipesFragment(it))
                viewModel.onDetailNavigated()
            }
        }



        return binding.root
    }


}