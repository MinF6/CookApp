package com.zongmin.cook.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val ingredientName: String = "",
    val quantity: String = " ",
    val unit: String = " "
) : Parcelable