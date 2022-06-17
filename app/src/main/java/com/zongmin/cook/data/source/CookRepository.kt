package com.zongmin.cook.data.source

import com.zongmin.cook.data.Recipes

interface CookRepository {

    suspend fun getRecipes(): Result<List<Recipes>>
}