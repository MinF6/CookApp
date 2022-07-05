package com.zongmin.cook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Summary(
    var id: String = " ",
    var name: String = " ",
    var category: String = " ",
    var mainImage: String = " ",
    var serving: Int = 0,
    var cookingTime: String = " ",
    var tags: List<String> = listOf(" "),
    var author: String = " ",
    var like: List<String> = listOf(" "),
    var remark: String = " ",
    var references: List<String> = listOf(" "),
) : Parcelable