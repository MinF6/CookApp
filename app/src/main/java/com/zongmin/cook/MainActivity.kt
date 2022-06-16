package com.zongmin.cook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zongmin.cook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()

        val name = Test()
        binding.buttonTest.setOnClickListener {
            Log.d("hank1", "按下按鈕")
            name.name = "9527"
            db.collection("test").add(name).addOnSuccessListener {
                Log.d("hank1", "成功 id =>${it}")
//                Log.d("hank1", "新增了 =>${article}")
            }
                .addOnFailureListener {
                    Log.d("hank1", "失敗,${it}")
                }


        }

    }
}