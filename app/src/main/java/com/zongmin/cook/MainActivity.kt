package com.zongmin.cook

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.zongmin.cook.databinding.ActivityMainBinding
import com.zongmin.cook.util.CurrentFragmentType

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.imageActivityCreate.setOnClickListener {
            findNavController(R.id.myNavHostFragment).navigate(
                NavigationDirections.navigateToEditRecipesFragment(
                    null
                )
            )
        }
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupBottomNav()
        setupNavController()

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
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToRecipesFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_plan -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToPlanFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_management -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToManagmentFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToProfileFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_social -> {
                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToSocialFragment())
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}