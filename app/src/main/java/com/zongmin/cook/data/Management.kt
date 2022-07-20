package com.zongmin.cook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Management(
    var id: String = " ",
    val userId: String = " ",
    val threeMeals: String = " ",
    val name: String = " ",
    val belong: String = " ",
    val quantity: String = " ",
    val unit: String = " ",
    val time: Long = 0L

) : Parcelable