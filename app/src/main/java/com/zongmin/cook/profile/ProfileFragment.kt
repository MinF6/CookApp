package com.zongmin.cook.profile

import android.os.Bundle
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

        val adapter = ProfileAdapter()

        binding.recyclerviewProfile.adapter = adapter

        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.user = it
            binding.textProfileName.text = it.name
            binding.textProfileFollows.text = it.follows.size.toString()
            binding.textProfileFans.text = it.fans.size.toString()
            binding.textProfileIntroduce.text = it.introduce

        })

        viewModel.recipes.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

//        binding.buttonProfileLogin.setOnClickListener {
//            findNavController().navigate(NavigationDirections.navigateToLogin())
//        }


//            binding.viewpagerProfile.let {
//                binding.tabsProfile.setupWithViewPager(it)
//                binding.tabsProfile.tabMode = TabLayout.MODE_SCROLLABLE;
//                it.adapter = RecipesAdapter(childFragmentManager)
//                it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabsRecipes))
//            }
//
//            buttonRecipesDialog.text = SimpleDateFormat("MM/dd").format(Date())
//
//            buttonNavNew.setOnClickListener {
////                this.findNavController().navigate(MainNavigationDirections.navigateToArticleFragment())
//                findNavController().navigate(NavigationDirections.navigateToEditRecipesFragment())
//            }
//
//            buttonRecipesDialog.setOnClickListener {
//                findNavController().navigate(NavigationDirections.navigateToDialogPlan())
////            Log.d("hank1","111111111111111111111")
//            }
//
//            return@onCreateView root









        return binding.root
    }


}