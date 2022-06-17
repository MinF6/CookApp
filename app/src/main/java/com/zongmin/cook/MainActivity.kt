package com.zongmin.cook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zongmin.cook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupBottomNav()

//        val db = FirebaseFirestore.getInstance()
//        val name = Test()
//        binding.buttonTest.setOnClickListener {
//            Log.d("hank1", "按下按鈕")
//            name.name = "9527"
//            db.collection("test").add(name).addOnSuccessListener {
//                Log.d("hank1", "成功 id =>${it}")
////                Log.d("hank1", "新增了 =>${article}")
//            }
//                .addOnFailureListener {
//                    Log.d("hank1", "失敗,${it}")
//                }
//
//
//        }


    }

    private fun setupBottomNav() {
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_recipes -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToRecipesFragment())
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_plan -> {

                    findNavController(R.id.myNavHostFragment).navigate(NavigationDirections.navigateToProfileFragment())
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