package com.zongmin.cook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.zongmin.cook.databinding.ActivityMainBinding
import com.zongmin.cook.recipes.RecipesViewModel
import com.zongmin.cook.util.CurrentFragmentType


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel = MainViewModel()



//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        for (fragment in supportFragmentManager.fragments) {
//            fragment.onActivityResult(requestCode, resultCode, data)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.imageActivityCreate.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToEditRecipesFragment(null))
        }
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

//        val parentViewModel = ViewModelProvider(requireParentFragment()).get(RecipesViewModel::class.java)
//        val mainViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
        setupBottomNav()
        setupNavController()




//      crash測試
//        val crashButton = Button(this)
//        crashButton.text = "Test Crash"
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        }
//
//        addContentView(crashButton, ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT))


    }

    private fun setupNavController() {
        findNavController(R.id.myNavHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            viewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.recipesFragment -> CurrentFragmentType.RECIPES
                R.id.planFragment -> CurrentFragmentType.PLAN
                R.id.managmentFragment -> CurrentFragmentType.MANAGEMENT
                R.id.profileFragment -> CurrentFragmentType.PROFILE
                R.id.detailRecipesFragment -> CurrentFragmentType.DETAIL
                R.id.socialFragment -> CurrentFragmentType.SOCIAL
                R.id.editRecipesFragment -> CurrentFragmentType.EDIT
                R.id.loginFragment -> CurrentFragmentType.LOGIN
                else -> viewModel.currentFragmentType.value
            }
        }
    }

    private fun setupBottomNav() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_recipes -> {
//                    binding.textActivityTitle.text = "食譜"
//                    binding.imageActivityCreate.visibility = View.VISIBLE
//                    binding.imageActivitySearch.visibility = View.VISIBLE
//                    binding.toolbar.visibility = View.VISIBLE
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToRecipesFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_plan -> {
//                    binding.textActivityTitle.text = "計畫"
//                    binding.imageActivityCreate.visibility = View.GONE
//                    binding.imageActivitySearch.visibility = View.GONE
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToPlanFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_management -> {
//                    binding.textActivityTitle.text = "採購"
//                    binding.imageActivityCreate.visibility = View.GONE
//                    binding.imageActivitySearch.visibility = View.GONE
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToManagmentFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_profile -> {
//                    binding.textActivityTitle.text = "個人"
//                    binding.imageActivityCreate.visibility = View.GONE
//                    binding.imageActivitySearch.visibility = View.GONE
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToProfileFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_social -> {
//                    binding.textActivityTitle.text = "分享"
//                    binding.imageActivityCreate.visibility = View.GONE
//                    binding.imageActivitySearch.visibility = View.GONE
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToSocialFragment())
                    return@setOnItemSelectedListener true
                }

            }
            false
        }

    }
}