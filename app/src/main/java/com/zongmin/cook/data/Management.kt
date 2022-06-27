package com.zongmin.cook.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


@Parcelize
data class Management(
    val id: String = " ",
    val threeMeals: String = " ",
    val name: String = " ",
    val belong: String = " ",
    val quantity: String = " ",
    val unit: String = " ",
    val time: Date? = null

) : Parcelable