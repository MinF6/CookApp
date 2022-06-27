package com.zongmin.cook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: String = " ",
    val name: String = " ",
    val creation: List<String> = listOf(" "),
    val blockList: List<String> = listOf(" "),
    val email: String = " ",
    val fans: List<String> = listOf(" "),
    val follows: List<String> = listOf(" "),
    val headShot: String = " ",
    val introduce: String = " "
) : Parcelable