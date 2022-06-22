package com.zongmin.cook.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.zongmin.cook.CookApplication
import com.zongmin.cook.data.*
import com.zongmin.cook.data.source.CookDataSource
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object CookRemoteDataSource : CookDataSource {


    override suspend fun getRecipes(): Result<List<Recipes>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(" Recipes")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    Log.d("hank1","check4")
                    var count = task.result.size()
                    val list = mutableListOf<Recipes>()
                    for (document in task.result!!) {
//                        Log.d("hank1",document.id + " => " + document.data)
                        val recipes = document.toObject(Recipes::class.java)
                        FirebaseFirestore.getInstance()
                            .collection(" Recipes")
                            .document(document.id)
                            .collection("ingredient")
                            .get()
                            .addOnCompleteListener { task2 ->
                                if (task2.isSuccessful) {
                                    val list2 = mutableListOf<Ingredient>()
                                    for (document2 in task2.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                        list2.add(document2.toObject(Ingredient::class.java))
                                    }
                                    recipes.ingredient = list2
//                                    list.add(recipes)
//                        Log.d("hank1", "看一下拿到的ingredient -> ${recipes.ingredient}")
//                                    count--
//                                    if (count == 0) {
//                                        continuation.resume(Result.Success(list))
//                                    }
                                }

                            }
                        FirebaseFirestore.getInstance()
                            .collection(" Recipes")
                            .document(document.id)
                            .collection("message")
                            .get()
                            .addOnCompleteListener { task3 ->
                                if (task3.isSuccessful) {
                                    val list2 = mutableListOf<Message>()
                                    for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                        list2.add(document2.toObject(Message::class.java))
                                    }
                                    recipes.message = list2
//                                    list.add(recipes)
//                        Log.d("hank1", "看一下拿到的ingredient -> ${recipes.ingredient}")
//                                    count--
//                                    if (count == 0) {
//                                        continuation.resume(Result.Success(list))
//                                    }
                                }

                            }
                        FirebaseFirestore.getInstance()
                            .collection(" Recipes")
                            .document(document.id)
                            .collection("step")
                            .get()
                            .addOnCompleteListener { task3 ->
                                if (task3.isSuccessful) {
                                    val list2 = mutableListOf<Step>()
                                    for (document2 in task3.result) {
//                                        Log.d("hank1", document2.id + " => " + document.data)
//                                        val ingredient = document.toObject(Ingredient::class.java)
                                        list2.add(document2.toObject(Step::class.java))
                                    }
                                    recipes.step = list2
                                    list.add(recipes)
//                        Log.d("hank1", "看一下拿到的ingredient -> ${recipes.ingredient}")
                                    count--
                                    if (count == 0) {
                                        continuation.resume(Result.Success(list))
                                    }
                                }

                            }


                    }

//                    list.add(recipes)
////                                    Log.d("hank1", "看一下拿到的ingredient -> ${recipes.ingredient}")
//                                    count--
//                                    if (count == 0) {
//                                        continuation.resume(Result.Success(list))
//                                    }

                } else {
                    task.exception?.let {
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
                }
            }
    }

    //備份----------------------------------------------

//    override suspend fun getRecipes(): Result<List<Recipes>> = suspendCoroutine { continuation ->
//        FirebaseFirestore.getInstance()
//            .collection(" Recipes")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
////                    Log.d("hank1","check4")
//                    val list = mutableListOf<Recipes>()
//                    for (document in task.result!!) {
//
////                        Log.d("hank1","check3")
////                        Log.d("hank1",document.id + " => " + document.data)
//
//                        val article = document.toObject(Recipes::class.java)
//
//                        list.add(article)
//                    }
//                    continuation.resume(Result.Success(list))
//                } else {
//                    task.exception?.let {
//                        continuation.resume(Result.Error(it))
//                        return@addOnCompleteListener
//                    }
////                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
//                }
//            }
//    }

    //備份----------------------------------------------
//    override suspend fun getIngredient(): Result<List<Ingredient>>
//    = suspendCoroutine { continuation ->
//            FirebaseFirestore.getInstance()
//                .collection(" Recipes")
//                .get()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
////                    Log.d("hank1","check4")
//                        var count = task.result.size()
//                        val list = mutableListOf<Recipes>()
//                        for (document in task.result!!) {
////                        Log.d("hank1",document.id + " => " + document.data)
//                            val ingredient = document.toObject(Ingredient::class.java)
//                            FirebaseFirestore.getInstance()
//                                .collection(" Recipes")
//                                .document(document.id)
//                                .collection("ingredient")
//                                .get()
//                                .addOnCompleteListener { task2 ->
//                                    if(task2.isSuccessful){
//                                        val list2 = mutableListOf<Ingredient>()
//                                    }
//
//                                }
//
//
//
////                            list.add(ingredient)
//                        }
//
//                        continuation.resume(Result.Success(list))
//                    } else {
//                        task.exception?.let {
//                            continuation.resume(Result.Error(it))
//                            return@addOnCompleteListener
//                        }
////                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
//                    }
//                }
//        }


}
