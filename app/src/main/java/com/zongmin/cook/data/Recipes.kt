package com.zongmin.cook.data

import android.os.Parcelable
import com.google.firebase.Timestamp.now
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.sql.Timestamp

@Parcelize
data class Recipes(
    var id: String = " ",
    var name: String = " ",
    var category: String = " ",
    var mainImage: String = " ",
    var serving: Int = 0,
    var cookingTime: String = " ",
    var ingredient: List<Ingredient> = listOf(Ingredient(" "," "," "," ")),
    var step: List<Step> = listOf(Step(" "," "," "," ",ToolType(" ", " "))),
    var tags: List<String> = listOf(" "),
    var author: String = " ",
    var like: List<String> = listOf(" "),
    var remark: String =" ",
    var references: List<String> = listOf(" "),
    var message: List<Message> = listOf(Message(" "," "," ",null))


): Parcelable

