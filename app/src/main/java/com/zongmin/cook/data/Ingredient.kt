package com.zongmin.cook.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val id: String = " ",
    @Json(name = "ingredient_name") val ingredientName: String = "",
    val unit: String = " "
) : Parcelable