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
import androidx.navigation.fragment.findNavController
import com.zongmin.cook.NavigationDirections
import com.zongmin.cook.login.UserManager


class SocialFragment : Fragment() {

    private val viewModel by viewModels<SocialViewModel> { getVmFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentSocialBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getPublicRecipesResult()

        binding.viewModel = viewModel

        val adapter = SocialAdapter(SocialAdapter.OnClickListener {
            viewModel.navigateToDetail(it)
        }, viewModel)

        binding.recyclerviewSocial.adapter = adapter

        viewModel.recipes.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.getUserList(it)
            }
        }

        viewModel.userList.observe(viewLifecycleOwner) {
            viewModel.setUserMap(it)
        }

        viewModel.userMap.observe(viewLifecycleOwner) {
            adapter.submitList(viewModel.recipes.value)
        }

        viewModel.user.observe(viewLifecycleOwner) {
            UserManager.user = it
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