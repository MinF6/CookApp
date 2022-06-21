package com.zongmin.cook.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.zongmin.cook.CookApplication
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.source.CookDataSource
import com.zongmin.cook.data.Result
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object CookRemoteDataSource: CookDataSource {


    override suspend fun getRecipes(): Result<List<Recipes>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(" Recipes")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("hank1","check4")
                    val list = mutableListOf<Recipes>()
                    for (document in task.result!!) {

                        Log.d("hank1","check3")
                        Log.d("hank1",document.id + " => " + document.data)

                        val article = document.toObject(Recipes::class.java)

                        list.add(article)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
//                    continuation.resume(Result.Fail(CookApplication.instance.getString(1)))
                }
            }
    }





}
