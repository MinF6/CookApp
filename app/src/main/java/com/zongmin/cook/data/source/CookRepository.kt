package com.zongmin.cook.data.source

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zongmin.cook.CookApplication
import com.zongmin.cook.data.Recipes
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.zongmin.cook.data.Result

interface CookRepository {

    suspend fun getRecipes(): Result<List<Recipes>>
//            = suspendCoroutine { continuation ->
//        FirebaseFirestore.getInstance()
//            .collection("Recipes")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val list = mutableListOf<Recipes>()
//                    for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
//
//                        val article = document.toObject(Recipes::class.java)
//
//                        list.add(article)
//                    }
//                    continuation.resume(Result.Success(list))
////                    continuation.resume(Result.success(list))
//                } else {
//                    task.exception?.let {
//
////                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
//                        continuation.resume(Result.Error(it))
////                        continuation.resume(Result.)
//                        return@addOnCompleteListener
//                    }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
//                }
//            }
//    }

}