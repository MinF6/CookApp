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
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.getPublicRecipesResult()

        binding.viewModel = viewModel

        val adapter = SocialAdapter(SocialAdapter.OnClickListener{
            Log.d("hank1","我點到的item是 -> $it")
            viewModel.navigateToDetail(it)
        },viewModel)

        binding.recyclerviewSocial.adapter = adapter

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
//            Log.d("hank1", "觀察一下拿到的 => $it")
            viewModel.getUserList(it)

//            adapter.submitList(it)
        })

        viewModel.userList.observe(viewLifecycleOwner){
//            Log.d("hank1","看看查到的User資料庫的列表 -> $it")
            viewModel.setUserMap(it)

        }

        viewModel.userMap.observe(viewLifecycleOwner){
            Log.d("hank1", "觀察一下拿到的 => $it")

            adapter.submitList(viewModel.recipes.value)


        }

        viewModel.navigateToDetail.observe(viewLifecycleOwner){
            findNavController().navigate(NavigationDirections.navigateToDetailRecipesFragment(it))
        }





        return binding.root
    }


}