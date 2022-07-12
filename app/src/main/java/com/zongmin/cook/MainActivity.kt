package com.zongmin.cook

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.zongmin.cook.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding



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

        setupBottomNav()

        this.setTitle("煮食")

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