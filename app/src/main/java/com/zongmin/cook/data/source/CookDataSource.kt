package com.zongmin.cook.data.source

import androidx.lifecycle.MutableLiveData
import com.zongmin.cook.data.Recipes

interface CookDataSource {

    suspend fun getRecipes():Result<Recipes>


//    suspend fun login(id: String): Result<Author>
//
//    suspend fun getArticles(): Result<List<Article>>
//
//    fun getLiveArticles(): MutableLiveData<List<Article>>
//
//    suspend fun publish(article: Article): Result<Boolean>
//
//    suspend fun delete(article: Article): Result<Boolean>


}
