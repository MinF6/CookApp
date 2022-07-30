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

        val adapter = SocialAdapter(SocialAdapter.OnClickListener {
            Log.d("hank1", "我點到的item是 -> $it")
            viewModel.navigateToDetail(it)
        }, viewModel)

        binding.recyclerviewSocial.adapter = adapter

        viewModel.recipe.observe(viewLifecycleOwner, Observer {
            Log.d("hank1", "觀察一下拿到的 => $it")
            if (it != null) {
                viewModel.getUserList(it)
            }


//            adapter.submitList(it)
        })

//        UserManager.user.collect
        viewModel.userList.observe(viewLifecycleOwner) {
//            Log.d("hank1","看看查到的User資料庫的列表 -> $it")
            viewModel.setUserMap(it)

        }

        viewModel.userMap.observe(viewLifecycleOwner) {
            Log.d("hank1", "觀察一下拿到的 => $it")

            adapter.submitList(viewModel.recipe.value)

        }

        viewModel.user.observe(viewLifecycleOwner){
            Log.d("hank1", "更新的UserData => $it")
            UserManager.user = it
//            viewModel.getPublicRecipesResult()
        }

        viewModel.like.observe(viewLifecycleOwner){
            Log.d("hank1", "有人改變了讚")
//            viewModel.getPublicRecipesResult()
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