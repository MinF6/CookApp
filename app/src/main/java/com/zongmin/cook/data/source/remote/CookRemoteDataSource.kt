package com.zongmin.cook.data.source.remote

import androidx.lifecycle.LiveData
import com.zongmin.cook.data.Recipes
import com.zongmin.cook.data.source.CookDataSource

object CookRemoteDataSource: CookDataSource {


    override suspend fun getRecipes(): Result<Recipes> {
        TODO("Not yet implemented")
    }

}
