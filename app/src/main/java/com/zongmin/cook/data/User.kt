package com.zongmin.cook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String = " ",
    var name: String = " ",
    val creation: List<String> = listOf(),
    val blockList: List<String> = listOf(),
    var email: String = " ",
    val fans: List<String> = listOf(),
    val follows: List<String> = listOf(),
    var headShot: String = " ",
    val introduce: String = " ",
    val collect: List<String> = listOf()

) : Parcelable