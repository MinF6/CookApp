package com.zongmin.cook.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    var id: String = "",
    var ingredientName: String = "",
    var quantity: String = "",
    var unit: String = ""
) : Parcelable