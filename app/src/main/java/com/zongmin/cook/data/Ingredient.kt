package com.zongmin.cook.data

import com.squareup.moshi.Json

data class Ingredient(
    val id: String = " ",
    @Json(name = "ingredient_name") val ingredientName: String = "",
    val unit: String = " "
)