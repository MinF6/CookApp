package com.zongmin.cook.data

import com.squareup.moshi.Json
import java.sql.Timestamp


data class Recipes(
    var id: String = " ",
    var name: String = " ",
    var category: String = " ",
    @Json(name = "main_image") val mainImage: String,
    var serving: Int = 0,
    @Json(name = "cooking_time") val cookingTime: Timestamp? = null,
    var ingredient: List<Ingredient>,
    var step: List<Step>,
    var tag: Array<String>,
    var author: String,
    var like: Array<String>,
    var remark: String,
    var references: String,
    var message: List<Message>

)

