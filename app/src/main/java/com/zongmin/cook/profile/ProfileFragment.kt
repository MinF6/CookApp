package com.zongmin.cook.profile

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.databinding.FragmentProfileBinding

import com.zongmin.cook.ext.getVmFactory


class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ProfileAdapter(ProfileAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        })
        binding.viewModel = viewModel
        binding.recyclerviewProfile.adapter = adapter

        viewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
            binding.textProfileTitle.text = "${it.name}發布的食譜"
        }

        viewModel.recipes.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.imageProfileNull.visibility = View.VISIBLE
                DrawableCompat.setTint(
                    binding.recyclerviewProfile.background,
                    Color.rgb(255, 255, 255)
                )
            }else{
                binding.imageProfileNull.visibility = View.GONE
            }
            adapter.submitList(it)
        }

        binding.textProfileFans.setOnClickListener {
            viewModel.user.value?.let { it1 -> viewModel.navigateToFollow(it1.fans) }
        }

        binding.textProfileFollows.setOnClickListener {
            viewModel.user.value?.let { it1 -> viewModel.navigateToFollow(it1.follows) }
        }

        viewModel.navigateToDetail.observe(viewLifecycleOwner) {
            if (it != null) {
                findNavController().navigate(NavigationDirections.navigateToDetailRecipesFragment(it))
                viewModel.onDetailNavigated()
            }
        }
        return binding.root
    }


}