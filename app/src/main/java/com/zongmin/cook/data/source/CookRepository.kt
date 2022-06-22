package com.zongmin.cook.data.source

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.zongmin.cook.CookApplication
import com.zongmin.cook.data.Ingredient
import com.zongmin.cook.data.Recipes
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import com.zongmin.cook.data.Result

interface CookRepository {

    suspend fun getRecipes(): Result<List<Recipes>>


//    suspend fun getIngredient(): Result<List<Ingredient>>


}